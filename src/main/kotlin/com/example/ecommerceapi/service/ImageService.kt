package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.queue.ImageTask
import com.example.ecommerceapi.queue.ImageTaskRepository
import com.example.ecommerceapi.queue.ImageTaskType
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
    val firebaseStorageService: FirebaseStorageService,
    val imageTaskRepository: ImageTaskRepository
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
        imageTaskRepository.save(ImageTask(type = ImageTaskType.WATERMARK, image = image))
        imageTaskRepository.save(ImageTask(type = ImageTaskType.UPSCALE, image = image))
        imageTaskRepository.save(ImageTask(type = ImageTaskType.DOWNSCALE, image = image))
        return CompletableFuture.completedFuture(true)
    }

    fun addWaterMark(imageData: ByteArray): ByteArray {
        println("WaterMarking image in Thread: ${Thread.currentThread().name}")
//        throw Exception("WaterMarking failed in thread : ${Thread.currentThread().name}")
        Thread.sleep(5000)
        return imageData
    }

    fun upscaleImage(imageData: ByteArray): ByteArray {
        println("UpScaling image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        return imageData
    }

    fun downScaleImage(imageData: ByteArray): ByteArray {
        println("DownScaling image in Thread: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        return imageData
    }
}