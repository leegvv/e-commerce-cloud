package net.arver.commerce.dao;

import java.util.Optional;
import net.arver.commerce.constant.BrandCategory;
import net.arver.commerce.constant.GoodsCategory;
import net.arver.commerce.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * GoodsDao.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface GoodsDao extends JpaRepository<Goods, Long> {

    Optional<Goods> findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
            GoodsCategory goodsCategory, BrandCategory brandCategory, String goodsName);
}
