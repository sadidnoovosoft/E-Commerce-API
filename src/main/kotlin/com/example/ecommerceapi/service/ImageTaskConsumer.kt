package com.example.ecommerceapi.service

import com.example.ecommerceapi.queue.*
import org.springframework.stereotype.Component

@Component
class ImageTaskConsumer(
    val imageService: ImageService
) : TaskConsumer {
    override val taskType: TaskType = TaskType.IMAGE_PROCESSING

    override fun processTask(task: Task) {
        val payload = task.payload
        val process = payload["process"].toString()
        val fileName = payload["fileName"].toString()
        val filePath = payload["filePath"].toString()
        val folderName = payload["folderName"].toString()
        when (process) {
            "WATERMARK" -> imageService.addWaterMark(fileName, filePath, folderName)
            "UPSCALE" -> imageService.upscaleImage(fileName, filePath, folderName)
            "DOWNSCALE" -> imageService.downscaleImage(fileName, filePath, folderName)
            else -> throw Exception("Invalid image process type")
        }
    }
}