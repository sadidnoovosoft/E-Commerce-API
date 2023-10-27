package com.example.ecommerceapi.utils

open class ResourceNotFoundException(message: String) : RuntimeException(message)

class UserNotFoundException(userId: Long) : ResourceNotFoundException("User with id $userId does not exist")
class ProductNotFoundException(productId: Long) : ResourceNotFoundException("Product with id $productId does not exist")
class OrderNotFoundException(orderId: Long) : ResourceNotFoundException("Order with id $orderId does not exist")