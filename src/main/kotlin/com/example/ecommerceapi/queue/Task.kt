package com.example.ecommerceapi.queue

import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import java.time.LocalDateTime

@Entity
class Task(
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: TaskType,

    @Column(columnDefinition = "jsonb")
    @Convert(converter = MapJsonConverter::class)
    @ColumnTransformer(write = "?::jsonb")
    val payload: Map<String, Any>,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: TaskStatus = TaskStatus.PENDING,

    @Column(name = "last_attempt_time")
    var lastAttemptTime: LocalDateTime? = null,

    @Column(name = "next_attempt_time")
    var nextAttemptTime: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "last_attempt_error_message")
    var lastAttemptErrorMessage: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}

enum class TaskStatus {
    PENDING,
    ERROR,
    SUCCESS
}

enum class TaskType {
    IMAGE_PROCESSING
}