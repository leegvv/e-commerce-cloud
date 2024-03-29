package net.arver.commerce.converter;

import javax.persistence.AttributeConverter;
import net.arver.commerce.constant.BrandCategory;

/**
 * BrandCategoryConverter.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {
    @Override
    public String convertToDatabaseColumn(final BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(final String code) {
        return BrandCategory.of(code);
    }
}
