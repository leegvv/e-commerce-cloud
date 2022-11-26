package net.arver.commerce.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.TableId;
import net.arver.commerce.constant.GoodsConstant;
import net.arver.commerce.dao.GoodsDao;
import net.arver.commerce.entity.Goods;
import net.arver.commerce.goods.DeductGoodsInventory;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.goods.SimpleGoodsInfo;
import net.arver.commerce.service.GoodsService;
import net.arver.commerce.util.JsonUtil;
import net.arver.commerce.vo.PageSimpleGoodsInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * GoodsServiceImpl.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    private final StringRedisTemplate stringRedisTemplate;

    private final GoodsDao goodsDao;

    public GoodsServiceImpl(final StringRedisTemplate stringRedisTemplate, final GoodsDao goodsDao) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.goodsDao = goodsDao;
    }


    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(final TableId tableId) {
        final List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get goods info by ids: [{}]", JsonUtil.toJson(ids));
        final List<Goods> goodsList = goodsDao.findAllById(ids);
        return goodsList.stream().map(Goods::toGoodsInfo).collect(Collectors.toList());
    }

    @Override
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(final int page) {
        // 分页不能从 redis cache 中去拿
        int pageNo = page;
        if (pageNo <= 1) {
            pageNo = 1;
        }
        final Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("id").descending());
        final Page<Goods> pageData = goodsDao.findAll(pageable);

        // 是否还有更多页
        final boolean hasMore = pageData.getTotalPages() > page;
        return new PageSimpleGoodsInfo(
                pageData.getContent().stream()
                        .map(Goods::toSimple).collect(Collectors.toList()),
                hasMore
        );
    }

    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(final TableId tableId) {
        final List<Object> goodsIds = tableId.getIds().stream()
                .map(item -> item.getId().toString())
                .collect(Collectors.toList());
        // 如果 cache 中查不到 goodsId 对应的数据， 返回的时null, [null, null]
        final List<Object> cachedSimpleGoodsInfos = stringRedisTemplate.opsForHash()
                .multiGet(GoodsConstant.GOODS_KEY, goodsIds)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(cachedSimpleGoodsInfos)) {
            // 1. 如果从缓存中查询出所有需要的 SimpleGoodsInfo
            if (cachedSimpleGoodsInfos.size() == goodsIds.size()) {
                log.info("get simple goods info by ids (from cache): [{}]", JsonUtil.toJson(goodsIds));
                return parseCachedGoodsInfo(cachedSimpleGoodsInfos);
            } else {
                // 2. 一部分从 redis cache 中获取（left）, 一部分从数据表中获取（right）
                final List<SimpleGoodsInfo> left = parseCachedGoodsInfo(cachedSimpleGoodsInfos);
                // 取差集： 要查询的数据 - 缓存中查到的 = 缓存中没有的（需要重数据库中获取）
                final Collection<Long> subtractIds = CollectionUtils.subtract(
                        goodsIds.stream().map(g -> Long.valueOf(g.toString())).collect(Collectors.toList()),
                        left.stream().map(SimpleGoodsInfo::getId).collect(Collectors.toList())
                );
                // 缓存中没有的，查询数据表并缓存
                final List<SimpleGoodsInfo> right = queryGoodsFromDBAndDCacheToRedis(
                        new TableId(subtractIds.stream().map(TableId.Id::new).collect(Collectors.toList()))
                );
                // 合并 left 和 right 并返回
                log.info("get simple goods info by ids (from db and cache): [{}]", JsonUtil.toJson(subtractIds));
                return new ArrayList<>(CollectionUtils.union(left, right));
            }
        } else {
            return queryGoodsFromDBAndDCacheToRedis(tableId);
        }
    }

    private List<SimpleGoodsInfo> parseCachedGoodsInfo(final List<Object> cachedSimpleGoodsInfo) {
        return cachedSimpleGoodsInfo.stream()
                .map(s -> JsonUtil.parse(s.toString(), new TypeReference<SimpleGoodsInfo>() {}))
                .collect(Collectors.toList());
    }

    private List<SimpleGoodsInfo> queryGoodsFromDBAndDCacheToRedis(final TableId tableId) {
        // 从数据表中查询数据并做转换
        final List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get simple goods info by ids （from db）: [{}]", JsonUtil.toJson(ids));
        final List<Goods> goods = goodsDao.findAllById(ids);

        // 将结果缓存到 redis
        log.info("cache goods info: [{}]", JsonUtil.toJson(ids));

        final List<SimpleGoodsInfo> result = goods.stream()
                .map(Goods::toSimple).collect(Collectors.toList());
        final Map<Long, String> id2JsonObject = result.stream()
                .collect(Collectors.toMap(SimpleGoodsInfo::getId, g -> JsonUtil.toJson(g)));
        // 保存到 redis
        stringRedisTemplate.opsForHash().putAll(GoodsConstant.GOODS_KEY, id2JsonObject);
        return result;
    }

    @Override
    @Transactional
    public Boolean deductGoodsInventory(final List<DeductGoodsInventory> deductGoodsInventories) {
        deductGoodsInventories.forEach(item -> {
            if (item.getCount() <= 0) {
                throw new RuntimeException("purchase goods count need > 0");
            }
        });
        final List<Goods> goods = goodsDao.findAllById(
                deductGoodsInventories.stream().map(DeductGoodsInventory::getGoodsId).collect(Collectors.toList())
        );
        if (goods.size() != deductGoodsInventories.size()) {
            throw new RuntimeException("request is not valid");
        }
        final Map<Long, DeductGoodsInventory> goodsId2Inventory = deductGoodsInventories.stream()
                .collect(Collectors.toMap(DeductGoodsInventory::getGoodsId, Function.identity()));
        // 检查是不是可以扣减库存，再去扣减库存
        goods.forEach(item -> {
            final Long currentInventory = item.getInventory();
            final Integer needDeductInventory = goodsId2Inventory.get(item.getId().toString()).getCount();
            if (currentInventory < needDeductInventory) {
                log.error("goods inventory is not enough: [{}], [{}]", currentInventory, needDeductInventory);
                throw new RuntimeException("goods inventory is not enough: " + item.getId());
            }
            // 扣减库存
            item.setInventory(currentInventory - needDeductInventory);
            log.info("deduct goods inventory: [{}], [{}], [{}]", item.getId(), currentInventory, item.getInventory());
        });

        goodsDao.saveAll(goods);
        log.info("deduct goods inventory done");

        return null;
    }
}
