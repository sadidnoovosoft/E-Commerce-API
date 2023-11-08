package com.example.ecommerceapi.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class FirebaseStorageService(
    @Value("\${myapp.firebase-bucket}") private val bucketName: String,
    @Value("\${myapp.firebase-downloadURL}") private val downloadURL: String,
) {
    fun uploadFile(imageData: ByteArray, fileName: String): String {
        val blobId = BlobId.of(bucketName, fileName)
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build()
        val credentialsResource = ClassPathResource("serviceAccountKey.json")
        val credentials = GoogleCredentials.fromStream(credentialsResource.inputStream)
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service
        storage.create(blobInfo, imageData)
        return String.format(downloadURL, URLEncoder.encode(fileName, StandardCharsets.UTF_8))
    }
}