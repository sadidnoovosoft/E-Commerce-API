package com.example.ecommerceapi.service.fileuploadstrategy

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
class FirebaseFileUpload(
    @Value("\${myapp.firebase-bucket}") private val bucketName: String,
    @Value("\${myapp.firebase-downloadURL}") private val downloadURL: String,
) : FileUploadStrategy{
    override fun uploadFile(data: ByteArray, fileName: String, folderName: String): String {
        val objectName = "$folderName/$fileName"
        val blobId = BlobId.of(bucketName, objectName)
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build()
        val credentialsResource = ClassPathResource("serviceAccountKey.json")
        val credentials = GoogleCredentials.fromStream(credentialsResource.inputStream)
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service
        storage.create(blobInfo, data)
        return String.format(downloadURL, URLEncoder.encode(fileName, StandardCharsets.UTF_8))
    }
}