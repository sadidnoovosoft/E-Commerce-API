package com.example.ecommerceapi.model

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val category: Category,
)

enum class Category {
    ELECTRONICS,
    FASHION,
    FURNITURE
}