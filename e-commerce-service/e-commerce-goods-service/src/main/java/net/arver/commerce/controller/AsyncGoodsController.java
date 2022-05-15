package net.arver.commerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.service.async.AsyncTaskManager;
import net.arver.commerce.vo.AsyncTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AsyncGoodsController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@RestController
@RequestMapping("/async-goods")
public class AsyncGoodsController {

    private final AsyncTaskManager asyncTaskManager;

    public AsyncGoodsController(final AsyncTaskManager asyncTaskManager) {
        this.asyncTaskManager = asyncTaskManager;
    }

    @PostMapping("/import-goods")
    public AsyncTaskInfo importGoods(@RequestBody final List<GoodsInfo> goodsInfos) {
        return asyncTaskManager.submit(goodsInfos);
    }

    @GetMapping("/task-info")
    public AsyncTaskInfo getTaskInf(@RequestParam final String taskId) {
        return asyncTaskManager.getTaskInfo(taskId);
    }


}
