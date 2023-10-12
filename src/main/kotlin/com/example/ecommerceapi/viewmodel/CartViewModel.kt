package com.example.ecommerceapi.viewmodel

data class CartViewModel(
    val userId: Long,
    val totalCartItems: Long,
    val totalPrice: Double,
    val cartItems: List<CartItemViewModel>
)