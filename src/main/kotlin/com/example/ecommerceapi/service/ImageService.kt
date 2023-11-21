package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.queue.TaskProducer
import com.example.ecommerceapi.queue.TaskType
import com.example.ecommerceapi.repository.ImageRepository
import com.example.ecommerceapi.service.fileuploadstrategy.FileUploadStrategy
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.util.UUID
import java.util.concurrent.CompletableFuture

@Service
class ImageService(
    val imageRepository: ImageRepository,
    val taskProducer: TaskProducer,
    val fileUploadStrategy: FileUploadStrategy
) {
    @Async
    fun uploadImage(imageData: ByteArray): CompletableFuture<Boolean> {
        val folderName = UUID.randomUUID().toString().replace("-", "")
        val fileName = "$folderName.png"
        val filePath = fileUploadStrategy.uploadFile(imageData, fileName, folderName)
        imageRepository.save(Image(fileName, folderName, "image/png", filePath))
        val payload = mapOf(
            "fileName" to fileName, "filePath" to filePath, "folderName" to folderName
        )
        taskProducer.enqueueTask(TaskType.IMAGE_PROCESSING, payload + mapOf("process" to "WATERMARK"))
        taskProducer.enqueueTask(TaskType.IMAGE_PROCESSING, payload + mapOf("process" to "UPSCALE"))
        taskProducer.enqueueTask(TaskType.IMAGE_PROCESSING, payload + mapOf("process" to "DOWNSCALE"))
        return CompletableFuture.completedFuture(true)
    }

    fun addWaterMark(fileName: String, filePath: String, folderName: String) {
        val imageData: ByteArray = Files.readAllBytes(File(filePath).toPath())
        println("WaterMarking image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        fileUploadStrategy.uploadFile(imageData, "${folderName}_WATERMARK.png", folderName)
    }

    fun downscaleImage(fileName: String, filePath: String, folderName: String) {
        val imageData: ByteArray = Files.readAllBytes(File(filePath).toPath())
        println("DownScaling image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        fileUploadStrategy.uploadFile(imageData, "${folderName}_DOWNSCALE.png", folderName)
    }

    fun upscaleImage(fileName: String, filePath: String, folderName: String) {
        val imageData: ByteArray = Files.readAllBytes(File(filePath).toPath())
        println("UpScaling image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        fileUploadStrategy.uploadFile(imageData, "${folderName}_UPSCALE.png", folderName)
    }
}