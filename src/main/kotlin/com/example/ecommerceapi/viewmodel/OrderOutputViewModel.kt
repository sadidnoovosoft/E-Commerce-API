package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.OrderStatus
import java.time.LocalDate

data class OrderOutputViewModel(
    val orderId: Long,
    val userId: Long,
    val product: ProductViewModel,
    val quantity: Long,
    val orderStatus: OrderStatus,
    val totalPrice: Double,
    val orderDate: LocalDate
)