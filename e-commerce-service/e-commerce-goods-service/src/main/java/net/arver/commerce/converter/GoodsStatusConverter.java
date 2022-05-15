package net.arver.commerce.converter;

import net.arver.commerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;

/**
 * 商品状态枚举属性转换器.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class GoodsStatusConverter implements AttributeConverter<GoodsStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final GoodsStatus goodsStatus) {
        return goodsStatus.getStatus();
    }

    @Override
    public GoodsStatus convertToEntityAttribute(final Integer status) {
        return GoodsStatus.of(status);
    }
}
