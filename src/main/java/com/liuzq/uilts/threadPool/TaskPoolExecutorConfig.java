package com.liuzq.uilts.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池配置类
 *
 * @author liuzq
 * @date 2019-10-21
 */
@Configuration
public class TaskPoolExecutorConfig {


	@Bean("taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		executor.setCorePoolSize(availableProcessors);
		executor.setMaxPoolSize(availableProcessors);
		// 设置队列长度较大,尽量避免任务被抛弃数
		executor.setQueueCapacity(Integer.MAX_VALUE);
		// 空闲线程30秒失效
		executor.setKeepAliveSeconds(30);
		executor.setThreadNamePrefix("taskExecutor-");
		// 为了不导致报告服务阻塞,使用拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		return executor;
	}
}
