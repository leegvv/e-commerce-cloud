package net.arver.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.arver.commerce.constant.AsyncTaskStatusEnum;

import java.util.Date;

/**
 * AsyncTaskInfo.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsyncTaskInfo {

    /**
     * 异步任务id.
     */
    private String taskId;

    /**
     * 状态.
     */
    private AsyncTaskStatusEnum status;

    /**
     * 开始时间.
     */
    private Date startTime;

    /**
     * 结束时间.
     */
    private Date endTime;

    /**
     * 总耗时.
     */
    private String totalTime;
}
