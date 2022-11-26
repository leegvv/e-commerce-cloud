package net.arver.commerce.constant;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * GoodsStatus.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Getter
@AllArgsConstructor
public enum GoodsStatus {

    /**
     * 上线.
     */
    ONLINE(101, "上线"),
    /**
     * 下线.
     */
    OFFLINE(102, "下线"),
    /**
     * 缺货.
     */
    STOCK_OUT(103, "缺货");

    /**
     * 状态码.
     */
    private final Integer status;

    /**
     * 状态描述.
     */
    private final String description;

    public static GoodsStatus of(final Integer status) {
        Objects.requireNonNull(status);
        return Stream.of(values())
                .filter(bean -> bean.status.equals(status))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(status + " not exists"));
    }
}
