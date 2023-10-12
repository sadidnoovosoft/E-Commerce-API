package com.example.ecommerceapi.model

data class CartItem(
    val userId: Long,
    val productId: Long,
    val quantity: Long,
)