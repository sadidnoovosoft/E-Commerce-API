package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.repository.ImageRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.CompletableFuture

@Service
class ImageService(
    val imageRepository: ImageRepository,
    val firebaseStorageService: FirebaseStorageService,
) {
    @Async
    fun uploadImage(imageData: ByteArray): CompletableFuture<Image> {
        println("Thread name : ${Thread.currentThread().name}")
        val fileName = UUID.randomUUID().toString().replace("-", "") + ".png"
        val filePath = firebaseStorageService.uploadFile(imageData, fileName)
        val image = Image(fileName, "image/jpg", filePath)
        imageRepository.save(image)
        return CompletableFuture.completedFuture(image)
    }
}