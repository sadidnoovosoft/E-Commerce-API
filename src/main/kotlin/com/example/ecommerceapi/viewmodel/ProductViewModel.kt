package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Category

data class ProductViewModel(
    val productId: Long,
    val name: String,
    val price: Double,
    val description: String,
    val category: Category
)