package com.example.ecommerceapi.queue

import com.example.ecommerceapi.service.ImageService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
class ImageTaskService(
    val imageTaskRepository: ImageTaskRepository,
    val imageService: ImageService,
    @Qualifier("asyncExecutor")
    val taskExecutor: ThreadPoolTaskExecutor
) {
    @PostConstruct
    fun init() {
        startProcessing()
    }

    fun startProcessing() {
        taskExecutor.execute {
            while (true) {
                val imageTasks = imageTaskRepository.findByNextAttemptTimeLessThanAndStatusOrderByNextAttemptTime(
                    LocalDateTime.now(), ImageTaskStatus.PENDING, PageRequest.of(0, 10)
                )
                imageTasks.forEach {
                    taskExecutor.execute { processTask(it) }
                }
                println("Thread name: ${Thread.currentThread().name}")
                Thread.sleep(10000)
            }
        }
    }

    fun processTask(task: ImageTask) {
        task.lastAttemptTime = LocalDateTime.now()
        try {
            when (task.type) {
                ImageTaskType.WATERMARK -> addWaterMark(task)
                ImageTaskType.UPSCALE -> upscaleImage(task)
                else -> downscaleImage(task)
            }
            task.status = ImageTaskStatus.SUCCESS
        } catch (e: Exception) {
            task.status = ImageTaskStatus.ERROR
            task.lastAttemptErrorMessage = e.message
            e.printStackTrace()
        }
        task.nextAttemptTime = null
        imageTaskRepository.save(task)
    }

    fun addWaterMark(task: ImageTask) {
        val image = task.image
        val imageData: ByteArray = Files.readAllBytes(File(image.filePath).toPath())
        val updatedImageData = imageService.addWaterMark(imageData)
        imageService.uploadImageToFileSystem(updatedImageData, "${image.folderName}_WATERMARK.png", image.folderName)
    }

    fun upscaleImage(task: ImageTask) {
        val image = task.image
        val imageData: ByteArray = Files.readAllBytes(File(image.filePath).toPath())
        val updatedImageData = imageService.upscaleImage(imageData)
        imageService.uploadImageToFileSystem(updatedImageData, "${image.folderName}_UPSCALE.png", image.folderName)
    }

    fun downscaleImage(task: ImageTask) {
        val image = task.image
        val imageData: ByteArray = Files.readAllBytes(File(image.filePath).toPath())
        val updatedImageData = imageService.downScaleImage(imageData)
        imageService.uploadImageToFileSystem(updatedImageData, "${image.folderName}_DOWNSCALE.png", image.folderName)
    }
}