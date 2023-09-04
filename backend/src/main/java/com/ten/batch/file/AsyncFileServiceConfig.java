package com.ten.batch.file;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 名称：AsyncFileServiceConfig
 * 功能描述：
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/29 16:16
 */


/**
 *  线程池配置        @Async("asyncOrderService")
 * @EnableAsync开始对异步任务的支持，然后使用@ConfigurationProperties把同类配置信息自动封装成一个实体类
 * @ConfigurationProperties属性prefix表示application.yml配置文件中配置项的前缀，最后结合Lombok的@Setter保证配置文件的值能够注入到该类对应的属性中
 *
 **/

@Setter
@ConfigurationProperties(prefix = "async.film-job")
@EnableAsync
@Configuration
public class AsyncFileServiceConfig {
    /**
     * 核心线程数（默认线程数）
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private int keepAliveSeconds;
    /**
     * 缓冲队列大小
     */
    private int queueCapacity;
    /**
     * 线程池名前缀
     */
    private String threadNamePrefix;

    @Bean
    public ThreadPoolTaskExecutor asyncFilmService() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 完成任务自动关闭 , 默认为false
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 核心线程超时退出，默认为false
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}

