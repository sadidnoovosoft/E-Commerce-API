package com.example.ecommerceapi.model

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val categoryId: Long,
)
