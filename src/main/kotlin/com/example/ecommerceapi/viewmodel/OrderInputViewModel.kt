package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Order
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.model.User

data class OrderInputViewModel(
    val productId: Long,
    val quantity: Long,
) {
    fun toOrder(product: Product, user: User) = Order(quantity = quantity, product = product, user = user)
}
