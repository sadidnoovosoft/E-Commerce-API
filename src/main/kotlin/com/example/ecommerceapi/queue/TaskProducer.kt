package com.example.ecommerceapi.queue

import org.springframework.stereotype.Component

@Component
class TaskProducer(val taskRepository: TaskRepository) {

    fun enqueueTask(taskType: TaskType, payload: Map<String, Any>): Boolean {
        return try {
            val task = Task(taskType, payload)
            taskRepository.save(task)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun enqueueTask(task: Task): Boolean {
        return try {
            taskRepository.save(task)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}