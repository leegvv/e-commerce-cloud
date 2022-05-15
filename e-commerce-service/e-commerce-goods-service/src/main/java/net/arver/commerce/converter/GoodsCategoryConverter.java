package net.arver.commerce.converter;

import net.arver.commerce.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * GoodsCategoryConverter.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory, String> {
    @Override
    public String convertToDatabaseColumn(final GoodsCategory goodsCategory) {
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategory convertToEntityAttribute(final String code) {
        return GoodsCategory.of(code);
    }
}
