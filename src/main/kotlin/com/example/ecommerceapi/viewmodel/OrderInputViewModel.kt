package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Order
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.model.User
import jakarta.validation.constraints.Positive

data class OrderInputViewModel(
    val productId: Long,
    @field:Positive
    val quantity: Long,
) {
    fun toOrder(product: Product, user: User) = Order(quantity = quantity, product = product, user = user)
}
