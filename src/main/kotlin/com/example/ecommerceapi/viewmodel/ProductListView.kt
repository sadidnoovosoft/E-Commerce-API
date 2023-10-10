package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Product

data class ProductListView(
    val totalProducts: Int,
    val products: List<Product>
)
