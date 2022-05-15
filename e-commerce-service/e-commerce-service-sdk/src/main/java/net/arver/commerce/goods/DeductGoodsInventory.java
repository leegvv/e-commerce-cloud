package net.arver.commerce.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扣减商品库存对象.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductGoodsInventory {

    /**
     * 商品主键 id.
     */
    private Long goodsId;

    /**
     * 扣减个数.
     */
    private Integer count;
}
