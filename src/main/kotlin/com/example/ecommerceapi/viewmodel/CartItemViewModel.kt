package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.CartItem
import com.example.ecommerceapi.model.Product

data class CartItemViewModel(
    val productId: Long,
    val quantity: Long,
) {
    fun toCartItem(product: Product) = CartItem(quantity, product)
}