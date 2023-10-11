package com.example.ecommerceapi.model

data class Cart(
    val id: Long,
    val cartItems: List<CartItem> = mutableListOf(),
    val userId: Long,
)
