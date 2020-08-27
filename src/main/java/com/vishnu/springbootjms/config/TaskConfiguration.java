package com.vishnu.springbootjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class to enable TaskExecutor
 *
 * @author : vishnu.g
 * created on : 26/Aug/2020
 */
@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfiguration {
    @Bean
    TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }
}
