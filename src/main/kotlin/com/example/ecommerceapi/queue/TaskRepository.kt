package com.example.ecommerceapi.queue

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TaskRepository : JpaRepository<Task, Long> {

    fun findByNextAttemptTimeLessThanAndStatusOrderByNextAttemptTime(
        dateTime: LocalDateTime,
        status: TaskStatus,
        page: Pageable
    ): List<Task>
    
}