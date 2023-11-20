package com.example.ecommerceapi.queue

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
class TaskExecutor(
    taskConsumers: List<TaskConsumer>,
    val taskRepository: TaskRepository,
    @Qualifier("asyncExecutor")
    val taskExecutor: ThreadPoolTaskExecutor
) {
    private val taskConsumerMap: Map<TaskType, TaskConsumer> = taskConsumers.associateBy { it.taskType }

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
                        val task = taskConsumerMap[it.type]?.processTask(it) ?: throw Exception("Invalid task type!")
                        taskRepository.save(task)
                    }
                }
                println("Thread name: ${Thread.currentThread().name}")
                Thread.sleep(10000)
            }
        }
    }
}