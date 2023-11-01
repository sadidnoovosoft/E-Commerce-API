package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.repository.ImageRepository
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.utils.ProductNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ImageService(
    val imageRepository: ImageRepository,
    val productRepository: ProductRepository,
    val firebaseStorageService: FirebaseStorageService,
) {
    fun uploadImage(productId: Long, file: MultipartFile): String {
        val product = productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }
        val fileName = UUID.randomUUID().toString().replace("-", "") + ".png"
        val filePath = firebaseStorageService.uploadFile(file, fileName)
        val image = Image(fileName, file.contentType, filePath)
        product.images += image
        imageRepository.save(image)
        return "File uploaded successfully: $filePath"
    }
}