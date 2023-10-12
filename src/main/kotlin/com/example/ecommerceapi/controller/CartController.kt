package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.CartItem
import com.example.ecommerceapi.service.CartService
import com.example.ecommerceapi.viewmodel.CartViewModel
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users/{userId}/cart")
class CartController(
    val cartService: CartService
) {
    @PostMapping
    fun addProductToCart(@PathVariable userId: Long, @RequestBody cartItem: CartItem): CartItem {
        return cartService.addProductToCart(userId, cartItem)
    }

    @GetMapping
    fun getCartItems(@PathVariable userId: Long): CartViewModel {
        return cartService.getCartItems(userId)
    }

    @DeleteMapping("/{productId}")
    fun removeCartItem(@PathVariable userId: Long, @PathVariable productId: Long) {
        return cartService.removeCartItem(userId, productId)
    }

    @DeleteMapping
    fun clearCart(@PathVariable userId: Long) {
        return cartService.clearCart(userId)
    }
}