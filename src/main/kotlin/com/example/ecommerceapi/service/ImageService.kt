package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Image
import com.example.ecommerceapi.repository.ImageRepository
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.utils.ProductNotFoundException
import com.example.ecommerceapi.utils.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.util.UUID

@Service
class ImageService(
    val imageRepository: ImageRepository,
    val productRepository: ProductRepository
) {
    private val folderPath = "C:/projects/E-commerce-API/Images/"

    fun uploadImage(productId: Long, file: MultipartFile): String {
        val product = productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }
        val fileName = UUID.randomUUID().toString().replace("-", "") + ".png"
        val filePath = folderPath + fileName
        val image = Image(fileName, file.contentType, filePath)
        product.images += image
        imageRepository.save(image)
        file.transferTo(File(filePath))
        return "File uploaded successfully: $filePath"
    }

    fun downloadImage(productId: Long, filename: String): ByteArray {
        val image = imageRepository.findByName(filename)
            .orElseThrow { ResourceNotFoundException("Image with name $filename not found") }
        val filePath = image.filePath
        return Files.readAllBytes(File(filePath).toPath())
    }
}