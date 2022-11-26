package net.arver.commerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AsyncTaskStatusEnum.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Getter
@AllArgsConstructor
public enum AsyncTaskStatusEnum {

    /**
     * 已经启动.
     */
    STARTED(0, "已经启动"),
    /**
     * 正在运行.
     */
    RUNNING(1, "正在运行"),
    /**
     * 执行成功.
     */
    SUCCESS(2, "执行成功"),
    /**
     * 执行失败.
     */
    FAILED(3, "执行失败");

    /**
     * 执行状态编码.
     */
    private final int state;

    /**
     * 执行状态描述.
     */
    private final String stateInfo;
}
