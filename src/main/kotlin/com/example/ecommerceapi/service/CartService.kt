package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.CartItem
import com.example.ecommerceapi.repository.CartItemRepository
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.repository.UserRepository
import com.example.ecommerceapi.utils.ProductNotFoundException
import com.example.ecommerceapi.utils.UserNotFoundException
import com.example.ecommerceapi.viewmodel.CartItemViewModel
import org.springframework.stereotype.Service

@Service
class CartService(
    val cartItemRepository: CartItemRepository,
    val productRepository: ProductRepository,
    val userRepository: UserRepository,
) {
    fun addProductToCart(userId: Long, cartItemViewModel: CartItemViewModel): CartItemViewModel {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val productId = cartItemViewModel.productId
        val product = productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }

        val cartItem = cartItemViewModel.toCartItem(product)
        cartItem.product = product
        user.cartItems += cartItem
        cartItemRepository.save(cartItem)

        return cartItem.toCartItemViewModel()
    }

    fun getCartItems(userId: Long): List<CartItem> {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        return user.cartItems
    }

    fun removeCartItem(userId: Long, cartItemId: Long) {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        user.cartItems = user.cartItems.filter { it.id != cartItemId }
        cartItemRepository.deleteById(cartItemId)
    }

    fun clearCart(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val cartItemsId = user.cartItems.map { it.id }
        user.cartItems = listOf()
        cartItemRepository.deleteAllById(cartItemsId)
    }
}