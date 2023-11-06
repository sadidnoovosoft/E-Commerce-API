package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.repository.ImageRepository
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.utils.ProductNotFoundException
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
import java.util.concurrent.CompletableFuture

@Service
class ImageService(
    val imageRepository: ImageRepository,
    val firebaseStorageService: FirebaseStorageService,
) {
    @Async
    fun uploadImage(file: MultipartFile): CompletableFuture<Image> {
        val fileName = UUID.randomUUID().toString().replace("-", "") + ".png"
        val filePath = firebaseStorageService.uploadFile(file, fileName)
        val image = Image(fileName, file.contentType, filePath)
        imageRepository.save(image)
        return CompletableFuture.completedFuture(image)
    }
}