package com.example.ecommerceapi.service.fileuploadstrategy

interface FileUploadStrategy {
    fun uploadFile(data: ByteArray, fileName: String, folderName: String): String
}