package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Product

data class ProductsViewModel(
    val totalProducts: Int,
    val products: List<ProductViewModel>
)
