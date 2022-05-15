package net.arver.commerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * GoodsCategory.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Getter
@AllArgsConstructor
public enum GoodsCategory {

    DIAN_QI("10001", "电器"),
    JIA_JI("10002", "家具"),
    FU_SYI("10003", "服饰"),
    MU_YIN("10004", "母婴"),
    SHI_PIN("10005", "食品"),
    TU_SHU("10006", "图书"),
    ;

    /**
     * code.
     */
    private final String code;

    /**
     * 描述.
     */
    private final String description;

    public static GoodsCategory of(final String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists"));
    }
}
