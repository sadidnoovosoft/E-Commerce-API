package com.example.ecommerceapi.queue

import com.example.ecommerceapi.service.ImageTaskConsumer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
class TaskExecutor(
    val imageTaskConsumer: ImageTaskConsumer,
    val taskRepository: TaskRepository,
    @Qualifier("asyncExecutor")
    val taskExecutor: ThreadPoolTaskExecutor
) {
    @PostConstruct
    fun init() {
        startProcessing()
    }

    fun startProcessing() {
        taskExecutor.execute {
            while (true) {
                val imageTasks = taskRepository.findByNextAttemptTimeLessThanAndStatusOrderByNextAttemptTime(
                    LocalDateTime.now(), TaskStatus.PENDING, PageRequest.of(0, 10)
                )
                imageTasks.forEach {
                    taskExecutor.execute {
                        when (it.type) {
                            TaskType.IMAGE_PROCESSING -> imageTaskConsumer.processTask(it)
                        }
                    }
                }
                println("Thread name: ${Thread.currentThread().name}")
                Thread.sleep(10000)
            }
        }
    }
}