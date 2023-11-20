package com.example.ecommerceapi.service

import com.example.ecommerceapi.queue.*
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
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
                "WATERMARK" -> addWaterMark(fileName, filePath, folderName)
                "UPSCALE" -> upscaleImage(fileName, filePath, folderName)
                "DOWNSCALE" -> downscaleImage(fileName, filePath, folderName)
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

    fun addWaterMark(fileName: String, filePath: String, folderName: String) {
        val imageData: ByteArray = Files.readAllBytes(File(filePath).toPath())
        println("WaterMarking image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        imageService.uploadImageToFileSystem(imageData, "${folderName}_WATERMARK.png", folderName)
    }

    fun downscaleImage(fileName: String, filePath: String, folderName: String) {
        val imageData: ByteArray = Files.readAllBytes(File(filePath).toPath())
        println("DownScaling image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        imageService.uploadImageToFileSystem(imageData, "${folderName}_DOWNSCALE.png", folderName)
    }

    fun upscaleImage(fileName: String, filePath: String, folderName: String) {
        val imageData: ByteArray = Files.readAllBytes(File(filePath).toPath())
        println("UpScaling image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        imageService.uploadImageToFileSystem(imageData, "${folderName}_UPSCALE.png", folderName)
    }
}