package net.arver.commerce.converter;

import javax.persistence.AttributeConverter;
import net.arver.commerce.constant.GoodsCategory;

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
