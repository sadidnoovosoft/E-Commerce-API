package com.example.ecommerceapi.viewmodel

data class CartItemViewModel(
    val productId: Long,
    val quantity: Long,
    val price: Double,
)