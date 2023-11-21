package com.example.ecommerceapi.service.fileuploadstrategy

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Service
class FileSystemUpload(
    @Value("\${myapp.file-system.folder-path}") private val folderPath: String
) : FileUploadStrategy {
    override fun uploadFile(data: ByteArray, fileName: String, folderName: String): String {
        val newFolderPath = folderPath + folderName
        File(newFolderPath).mkdirs()
        val filePath = "${newFolderPath}/${fileName}"
        Files.write(Paths.get(filePath), data)
        return filePath
    }
}