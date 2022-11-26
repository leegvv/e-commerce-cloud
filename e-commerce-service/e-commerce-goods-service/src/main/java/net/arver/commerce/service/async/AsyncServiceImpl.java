package net.arver.commerce.service.async;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.constant.GoodsConstant;
import net.arver.commerce.dao.GoodsDao;
import net.arver.commerce.entity.Goods;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * AsyncServiceImpl.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {
    private final GoodsDao goodsDao;

    private final StringRedisTemplate stringRedisTemplate;

    public AsyncServiceImpl(final GoodsDao goodsDao, final StringRedisTemplate stringRedisTemplate) {
        this.goodsDao = goodsDao;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 异步任务需要加上注解，并指定使用的线程池
     * 异步任务处理两件事：
     * 1. 将商品信息保存到数据表
     * 2. 更新商品缓存
     * @param goodsInfos 商品信息
     * @param taskId 任务id
     */
    @Async("getAsyncExecutor")
    @Override
    @Transactional
    public void asyncImportGoods(final List<GoodsInfo> goodsInfos, final String taskId) {
        log.info("async task running taskId: [{}]", taskId);
        final StopWatch watch = StopWatch.createStarted();

        // 1. 如果是 goodsInfo 中存在重复的商品， 不保存； 直接返回，记录错误日志
        // 请求数据是否合法的标记
        boolean isIllegal = false;

        // 将商品信息字段 joint 在一起， 用来判断是否存在重复
        final Set<String> goodsJointInfos = new HashSet<>(goodsInfos.size());
        // 过滤出来的，可以入库的商品信息
        final List<GoodsInfo> filteredGoodsInfo = new ArrayList<>(goodsInfos.size());
        for (final GoodsInfo goodsInfo : goodsInfos) {
            if (goodsInfo.getPrice() <= 0 || goodsInfo.getSupply() <= 0) {
                log.info("goods info is invalid: [{}]", JsonUtil.toJson(goodsInfo));
                continue;
            }

            // 组合商品信息
            final String jointInfo = String.format(
                    "%s,%s,%s",
                    goodsInfo.getGoodsCategory(),
                    goodsInfo.getBrandCategory(),
                    goodsInfo.getGoodsName()
            );
            if (filteredGoodsInfo.contains(jointInfo)) {
                isIllegal = true;
            }

            goodsJointInfos.add(jointInfo);
            filteredGoodsInfo.add(goodsInfo);
        }
        if (isIllegal || CollectionUtils.isEmpty(filteredGoodsInfo)) {
            watch.stop();
            log.warn("import nothing: [{}]", JsonUtil.toJson(filteredGoodsInfo));
            log.info("check and import goods done: [{}ms]", watch.getTime(TimeUnit.MILLISECONDS));
            return;
        }
        final List<Goods> goodsList = filteredGoodsInfo.stream().map(Goods::to).collect(Collectors.toList());
        final List<Goods> targetGoodsList = new ArrayList<>(goodsList.size());

        // 2. 保存 goodsInfo 之前判断下是否存在重复商品
        goodsList.forEach(g -> {
            if (null != goodsDao.findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
                    g.getGoodsCategory(), g.getBrandCategory(), g.getGoodsName()
            ).orElse(null)) {
                return;
            }
            targetGoodsList.add(g);
        });

        // 商品信息入口
        final List<Goods> goods = goodsDao.saveAll(targetGoodsList);

        saveNewGoodsInfoToRedis(goods);

        watch.stop();

        log.info("check and import goods success: [{}ms]", watch.getTime(TimeUnit.MILLISECONDS));
    }

    /**
     * 将保存到数据表中的数据缓存到 Redis 中.
     * dict: key -> id, SimpleGoodsInfo(json)
     * @param savedGoods 商品信息
     */
    private void saveNewGoodsInfoToRedis(final List<Goods> savedGoods) {
        final Map<String, String> id2JsonObject = savedGoods.stream()
                .map(Goods::toSimple)
                .collect(Collectors.toMap(g -> g.getId().toString(), g -> JsonUtil.toJson(g)));

        // 保存到 Redis 中
        stringRedisTemplate.opsForHash().putAll(GoodsConstant.GOODS_KEY, id2JsonObject);
    }
}
