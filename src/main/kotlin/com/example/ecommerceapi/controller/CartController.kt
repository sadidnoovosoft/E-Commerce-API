package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.CartItem
import com.example.ecommerceapi.service.CartService
import com.example.ecommerceapi.viewmodel.CartItemViewModel
import jakarta.validation.Valid
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
    fun addProductToCart(
        @PathVariable userId: Long,
        @Valid @RequestBody cartItemViewModel: CartItemViewModel
    ): CartItemViewModel {
        return cartService.addProductToCart(userId, cartItemViewModel)
    }

    @GetMapping
    fun getCartItems(@PathVariable userId: Long): List<CartItem> {
        return cartService.getCartItems(userId)
    }

    @DeleteMapping("/{cartItemId}")
    fun removeCartItem(@PathVariable userId: Long, @PathVariable cartItemId: Long) {
        return cartService.removeCartItem(userId, cartItemId)
    }

    @DeleteMapping
    fun clearCart(@PathVariable userId: Long) {
        return cartService.clearCart(userId)
    }
}