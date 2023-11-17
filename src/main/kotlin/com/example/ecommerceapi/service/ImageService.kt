package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.queue.TaskProducer
import com.example.ecommerceapi.queue.TaskType
import com.example.ecommerceapi.repository.ImageRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import java.util.concurrent.CompletableFuture

@Service
class ImageService(
    val imageRepository: ImageRepository,
    val taskProducer: TaskProducer,
    val firebaseStorageService: FirebaseStorageService,
) {
    private val folderPath = "C:/projects/E-commerce-API/Images/"

    fun uploadImageToFirebase(imageData: ByteArray, fileName: String): String {
        return firebaseStorageService.uploadFile(imageData, fileName)
    }

    fun uploadImageToFileSystem(imageData: ByteArray, fileName: String, folderName: String): String {
        println("Saving: $fileName in Thread ${Thread.currentThread().name}")
        val newFolderPath = folderPath + folderName
        File(newFolderPath).mkdirs()
        val filePath = "${newFolderPath}/${fileName}"
        Files.write(Paths.get(filePath), imageData)
        return filePath
    }

    @Async
    fun uploadImage(imageData: ByteArray): CompletableFuture<Boolean> {
        val folderName = UUID.randomUUID().toString().replace("-", "")
        val fileName = "$folderName.png"
        val filePath = uploadImageToFileSystem(imageData, fileName, folderName)
        val image = imageRepository.save(Image(fileName, folderName, "image/png", filePath))
        imageRepository.save(image)
        val payload = mapOf(
            "fileName" to fileName, "filePath" to filePath, "folderName" to folderName
        )
        taskProducer.enqueueTask(TaskType.IMAGE_PROCESSING, payload + mapOf("process" to "WATERMARK"))
        taskProducer.enqueueTask(TaskType.IMAGE_PROCESSING, payload + mapOf("process" to "UPSCALE"))
        taskProducer.enqueueTask(TaskType.IMAGE_PROCESSING, payload + mapOf("process" to "DOWNSCALE"))
        return CompletableFuture.completedFuture(true)
    }
}