package net.arver.commerce.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * GoodsInfo.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {

    private Long id;

    private String goodsCategory;

    private String brandCategory;

    private String goodsName;

    private String goodsPic;

    private String goodsDescription;

    private Integer goodsStatus;

    private Integer price;

    private Long supply;

    private Long inventory;

    private GoodsProperty goodsProperty;

    private Timestamp createTime;

    private Timestamp updateTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoodsProperty {

        /**
         * 尺寸.
         */
        private String size;

        /**
         * 颜色.
         */
        private String color;

        /**
         * 材质.
         */
        private String material;

        /**
         * 图案.
         */
        private String pattern;
    }
}
