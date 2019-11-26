package com.wzlue.web.common.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * springboot的线程池配置
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(TaskThreadPoolConfig.class)
public class ExecutorConfig {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private TaskThreadPoolConfig config;

    @Bean
    public Executor asyncPool(){
        logger.info("start async");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(config.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(config.getMaxPoolSize());
        //当核心线程都在跑任务，还有多余的任务会存到此处。
        executor.setQueueCapacity(config.getQueueCapacity());
        // 允许的空闲时间
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
