package net.arver.commerce.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.util.JsonUtil;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * AsyncPoolConfig.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@EnableAsync
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(60);
        // 非常重要，方便排查
        executor.setThreadNamePrefix("Goods-Async-");

        // 等待所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        // 定义拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 初始化线程池， 初始化 core 线程
        executor.initialize();

        return executor;
    }

    /**
     * 指定异步任务出现异常时使用到的处理器.
     * @return 处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /**
     * 异步任务异常捕获处理器.
     */
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(final Throwable ex, final Method method, final Object... params) {
            ex.printStackTrace();
            log.error("Async Task Error: [{}], Method: [{}], Param: [{}]",
                    ex.getMessage(), method.getName(), JsonUtil.toJson(params));

            // TODO: 发送右键或者是短信
        }
    }
}
