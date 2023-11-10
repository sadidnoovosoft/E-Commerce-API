package com.example.ecommerceapi.queue

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ImageTaskRepository : JpaRepository<ImageTask, Long> {
    fun findByNextAttemptTimeLessThanAndStatusOrderByNextAttemptTime(
        dateTime: LocalDateTime,
        status: ImageTaskStatus,
        page: Pageable
    ): List<ImageTask>

}