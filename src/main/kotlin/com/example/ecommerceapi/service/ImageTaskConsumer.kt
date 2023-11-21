package com.example.ecommerceapi.service

import com.example.ecommerceapi.queue.*
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ImageTaskConsumer(
    val imageService: ImageService
) : TaskConsumer {
    override val taskType: TaskType = TaskType.IMAGE_PROCESSING

    override fun processTask(task: Task): Task {
        val payload = task.payload
        val process = payload["process"].toString()
        val fileName = payload["fileName"].toString()
        val filePath = payload["filePath"].toString()
        val folderName = payload["folderName"].toString()
        task.lastAttemptTime = LocalDateTime.now()
        try {
            when (process) {
                "WATERMARK" -> imageService.addWaterMark(fileName, filePath, folderName)
                "UPSCALE" -> imageService.upscaleImage(fileName, filePath, folderName)
                "DOWNSCALE" -> imageService.downscaleImage(fileName, filePath, folderName)
                else -> throw Exception("Invalid image process type")
            }
            task.status = TaskStatus.SUCCESS
        } catch (e: Exception) {
            task.status = TaskStatus.ERROR
            task.lastAttemptErrorMessage = e.message
            e.printStackTrace()
        }
        task.nextAttemptTime = null
        return task
    }
}