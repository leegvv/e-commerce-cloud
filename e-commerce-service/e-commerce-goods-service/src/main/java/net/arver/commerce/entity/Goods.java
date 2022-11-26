package net.arver.commerce.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.arver.commerce.constant.BrandCategory;
import net.arver.commerce.constant.GoodsCategory;
import net.arver.commerce.constant.GoodsStatus;
import net.arver.commerce.converter.BrandCategoryConverter;
import net.arver.commerce.converter.GoodsCategoryConverter;
import net.arver.commerce.converter.GoodsStatusConverter;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.goods.SimpleGoodsInfo;
import net.arver.commerce.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Goods.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_goods", schema = "commerce")
@EntityListeners(AuditingEntityListener.class)
public class Goods {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "goods_category")
    @Convert(converter = GoodsCategoryConverter.class)
    private GoodsCategory goodsCategory;

    @Convert(converter = BrandCategoryConverter.class)
    @Column(name = "brand_category")
    private BrandCategory brandCategory;
    
    @Column(name = "goods_name")
    private String goodsName;
    
    @Column(name = "goods_pic")
    private String goodsPic;
    
    @Column(name = "goods_description")
    private String goodsDescription;

    @Convert(converter = GoodsStatusConverter.class)
    @Column(name = "goods_status")
    private GoodsStatus goodsStatus;
    
    @Column(name = "price")
    private Integer price;
    
    @Column(name = "supply")
    private Long supply;
    
    @Column(name = "inventory")
    private Long inventory;
    
    @Column(name = "goods_property")
    private String goodsProperty;

    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Timestamp updateTime;

    public static Goods to(final GoodsInfo goodsInfo) {
        final Goods goods = new Goods();
        BeanUtils.copyProperties(goodsInfo, goods,
                "goodsCategory", "brandCategory", "goodsProperty", "goodsStatus");
        goods.setGoodsCategory(GoodsCategory.of(goodsInfo.getGoodsCategory()));
        goods.setBrandCategory(BrandCategory.of(goodsInfo.getBrandCategory()));
        goods.setGoodsStatus(GoodsStatus.ONLINE); // 可以增加审核过程
        goods.setGoodsProperty(JsonUtil.toJson(goodsInfo.getGoodsProperty()));
        return goods;
    }

    public GoodsInfo toGoodsInfo() {
        final GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(this, goodsInfo,
                "goodsCategory", "brandCategory", "goodsProperty", "goodsStatus");
        goodsInfo.setGoodsCategory(this.getGoodsCategory().getCode());
        goodsInfo.setBrandCategory(this.getBrandCategory().getCode());
        goodsInfo.setGoodsStatus(this.getGoodsStatus().getStatus());
        goodsInfo.setGoodsProperty(JsonUtil.parse(this.getGoodsProperty(), GoodsInfo.GoodsProperty.class));
        return goodsInfo;
    }

    public SimpleGoodsInfo toSimple() {
        final SimpleGoodsInfo simpleGoodsInfo = new SimpleGoodsInfo();
        BeanUtils.copyProperties(this, simpleGoodsInfo);
        return simpleGoodsInfo;
    }
}
