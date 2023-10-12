package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.CartItem
import com.example.ecommerceapi.viewmodel.CartItemViewModel
import com.example.ecommerceapi.viewmodel.CartViewModel
import org.springframework.stereotype.Service

@Service
class CartService(
    val productService: ProductService
) {
    val cartItems = mutableListOf<CartItem>()

    fun addProductToCart(userId: Long, cartItem: CartItem): CartItem {
        val existingItem = cartItems.find {
            it.userId == userId && it.productId == cartItem.productId
        }
        if (existingItem == null) {
            cartItems.add(cartItem.copy(userId = userId))
        } else {
            cartItems.remove(existingItem)
            cartItems.add(cartItem.copy(userId = userId, quantity = cartItem.quantity + existingItem.quantity))
        }
        return cartItem
    }

    fun getCartItems(userId: Long): CartViewModel {
        val userCartItems = cartItems
            .filter { it.userId == userId }
            .map {
                CartItemViewModel(
                    productId = it.productId,
                    quantity = it.quantity,
                    price = it.quantity * productService.getProductPrice(it.productId)
                )
            }
        val totalPrice = userCartItems.sumOf(CartItemViewModel::price)
        return CartViewModel(userId, userCartItems.size.toLong(), totalPrice, userCartItems)
    }

    fun removeCartItem(userId: Long, productId: Long) {
        cartItems.removeIf { it.userId == userId && it.productId == productId }
    }

    fun clearCart(userId: Long) {
        cartItems.removeAll { it.userId == userId }
    }
}