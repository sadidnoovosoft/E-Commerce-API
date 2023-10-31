package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class ProductViewModel(
    val productId: Long,
    @field:NotBlank
    val name: String,
    @field:Positive
    val price: Double,
    val description: String,
    val category: Category,
    val images: List<String>
)