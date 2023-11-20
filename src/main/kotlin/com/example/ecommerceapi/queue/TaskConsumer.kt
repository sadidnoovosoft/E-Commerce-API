package com.example.ecommerceapi.queue

interface TaskConsumer {
    val taskType: TaskType
    fun processTask(task: Task): Task
}