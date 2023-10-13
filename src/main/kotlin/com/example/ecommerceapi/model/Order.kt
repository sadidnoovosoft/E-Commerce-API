package com.example.ecommerceapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class Order(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Long,
    val price: Double?,
    val status: OrderStatus?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val date: Date = Date()
)

enum class OrderStatus {
    CREATED, SHIPPED, DELIVERED, CANCELLED
}