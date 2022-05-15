package net.arver.commerce.service.async;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.constant.AsyncTaskStatusEnum;
import net.arver.commerce.goods.GoodsInfo;
import net.arver.commerce.vo.AsyncTaskInfo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 异步任务执行管理器.
 * 对异步任务进行包装管理，记录并塞入异步任务执行信息
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Component
public class AsyncTaskManager {

    private final AsyncService asyncService;

    private final Map<String, AsyncTaskInfo> taskContainer = new HashMap<>(16);

    public AsyncTaskManager(final AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    public AsyncTaskInfo initTask() {
        final AsyncTaskInfo taskInfo = new AsyncTaskInfo();
        taskInfo.setTaskId(UUID.randomUUID().toString());
        taskInfo.setStatus(AsyncTaskStatusEnum.STARTED);
        taskInfo.setStartTime(new Date());

        taskContainer.put(taskInfo.getTaskId(), taskInfo);
        return taskInfo;
    }

    public AsyncTaskInfo submit(final List<GoodsInfo> goodsInfos) {
        final AsyncTaskInfo taskInfo = initTask();
        asyncService.asyncImportGoods(goodsInfos, taskInfo.getTaskId());
        return taskInfo;
    }

    public void setTaskInfo(final AsyncTaskInfo taskInfo) {
        taskContainer.put(taskInfo.getTaskId(),  taskInfo);
    }

    public AsyncTaskInfo getTaskInfo(final String taskId) {
        return taskContainer.get(taskId);
    }
}
