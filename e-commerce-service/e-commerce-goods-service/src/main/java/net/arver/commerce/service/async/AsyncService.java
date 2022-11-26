package net.arver.commerce.service.async;

import java.util.List;
import net.arver.commerce.goods.GoodsInfo;

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
     * @param goodsInfos 商品信息
     * @param taskId 任务id
     */
    void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId);
}
