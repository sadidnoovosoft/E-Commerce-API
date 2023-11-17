package com.example.ecommerceapi.queue

interface TaskConsumer {
    fun processTask(task: Task)
}