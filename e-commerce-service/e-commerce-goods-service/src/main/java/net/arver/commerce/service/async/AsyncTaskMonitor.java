package net.arver.commerce.service.async;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.constant.AsyncTaskStatusEnum;
import net.arver.commerce.vo.AsyncTaskInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 异步任务执行切面监控.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Aspect
@Component
public class AsyncTaskMonitor {

    private final AsyncTaskManager asyncTaskManager;

    public AsyncTaskMonitor(final AsyncTaskManager asyncTaskManager) {
        this.asyncTaskManager = asyncTaskManager;
    }

    @Around("execution(* net.arver.commerce.service.async.AsyncServiceImpl.*(..))")
    public Object taskHandle(final ProceedingJoinPoint joinPoint) {
        // 获取 taskId, 调用异步任务传入的第二个参数
        final String taskId = joinPoint.getArgs()[1].toString();

        // 获取任务信息，在提交任务的时候就已经放入到容器中
        final AsyncTaskInfo taskInfo = asyncTaskManager.getTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task : [{}]", taskId);

        taskInfo.setStatus(AsyncTaskStatusEnum.RUNNING);

        AsyncTaskStatusEnum status;
        Object result;
        try {
            result = joinPoint.proceed();
            status = AsyncTaskStatusEnum.SUCCESS;
        } catch (final Throwable ex) {
            // 异步任务执行异常
            result = null;
            status = AsyncTaskStatusEnum.FAILED;
            log.error("AsyncTaskMonitor: async task [{}] is failed, Error Info: [{}]", taskId, ex.getMessage(), ex);
        }

        // 设置异步任务其他的信息，再次重新放入到容器中
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime(String.valueOf(taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime()));
        asyncTaskManager.setTaskInfo(taskInfo);

        return result;
    }

}
