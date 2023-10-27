package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.CartItem
import com.example.ecommerceapi.model.Product
import jakarta.validation.constraints.Positive

data class CartItemViewModel(
    val productId: Long,
    @field:Positive
    val quantity: Long,
) {
    fun toCartItem(product: Product) = CartItem(quantity, product)
}