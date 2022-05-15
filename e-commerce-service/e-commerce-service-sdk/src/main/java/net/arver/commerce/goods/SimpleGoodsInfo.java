package net.arver.commerce.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单商品信息.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGoodsInfo {

    private Long id;

    private String goodsName;

    private String goodsPic;

    private Integer price;

    public SimpleGoodsInfo(final Long id) {
        this.id = id;
    }
}
