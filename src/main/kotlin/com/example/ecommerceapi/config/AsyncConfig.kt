package com.example.ecommerceapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
@EnableAsync
class AsyncConfig {
    @Bean
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 5
        executor.queueCapacity = 500
        executor.setThreadNamePrefix("Spring-Async-")
        executor.initialize()
        return executor
    }
}