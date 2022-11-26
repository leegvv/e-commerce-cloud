package net.arver.commerce.constant;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 品牌分类.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Getter
@AllArgsConstructor
public enum BrandCategory {

    /**
     * 品牌 A.
     */
    BRAND_A("20001", "品牌 A"),
    /**
     * 品牌 B.
     */
    BRAND_B("20002", "品牌 B"),
    /**
     * 品牌 C.
     */
    BRAND_C("20003", "品牌 C"),
    /**
     * 品牌 D.
     */
    BRAND_D("20004", "品牌 D"),
    /**
     * 品牌 E.
     */
    BRAND_E("20005", "品牌 E");

    /**
     * 品牌分类编码.
     */
    private final String code;

    /**
     * 品牌分类描述信息.
     */
    private final String description;

    public static BrandCategory of(final String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(item -> item.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists"));
    }
}
