package net.arver.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.arver.commerce.goods.SimpleGoodsInfo;

import java.util.List;

/**
 * PageSimpleGoodsInfo.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSimpleGoodsInfo {

    /**
     * 分页简单商品信息.
     */
    private List<SimpleGoodsInfo> simpleGoodsInfos;

    /**
     * 是否有更多的商品（分页）
     */
    private Boolean hasMore;
}
