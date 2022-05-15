package net.arver.commerce.service.async;

import net.arver.commerce.goods.GoodsInfo;

import java.util.List;

/**
 * AsyncService.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface AsyncService {

    /**
     * 异步保存商品信息.
     * @param goodsInfos
     * @param taskId
     */
    void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId);
}
