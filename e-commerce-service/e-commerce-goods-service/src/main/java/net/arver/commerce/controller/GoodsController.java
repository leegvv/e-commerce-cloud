package net.arver.commerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.TableId;
import net.arver.commerce.goods.DeductGoodsInventory;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.goods.SimpleGoodsInfo;
import net.arver.commerce.service.GoodsService;
import net.arver.commerce.vo.PageSimpleGoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * GoodsController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(final GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/goods-info")
    public List<GoodsInfo> getGoodsInfoByTableId(@RequestBody final TableId tableId) {
        return goodsService.getGoodsInfoByTableId(tableId);
    }

    @GetMapping("page-simple-goods-info")
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(@RequestParam(required = false, defaultValue = "1")final int page) {
        return goodsService.getSimpleGoodsInfoByPage(page);
    }

    @PostMapping("/simple-goods-info")
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(@RequestBody final TableId tableId) {
        return goodsService.getSimpleGoodsInfoByTableId(tableId);
    }

    @PutMapping("/deduct-goods-inventory")
    public Boolean deductGoodsInventory(@RequestBody final List<DeductGoodsInventory> deductGoodsInventories) {
        return goodsService.deductGoodsInventory(deductGoodsInventories);
    }

}
