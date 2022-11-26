package net.arver.commerce.service;

import java.util.List;
import net.arver.commerce.account.TableId;
import net.arver.commerce.goods.DeductGoodsInventory;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.goods.SimpleGoodsInfo;
import net.arver.commerce.vo.PageSimpleGoodsInfo;

/**
 * GoodsService.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface GoodsService {

    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);

    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
}
