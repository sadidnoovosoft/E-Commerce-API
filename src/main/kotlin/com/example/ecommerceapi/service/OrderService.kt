package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.OrderStatus
import com.example.ecommerceapi.repository.CartItemRepository
import com.example.ecommerceapi.repository.OrderRepository
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.repository.UserRepository
import com.example.ecommerceapi.utils.OrderNotFoundException
import com.example.ecommerceapi.utils.ProductNotFoundException
import com.example.ecommerceapi.utils.UserNotFoundException
import com.example.ecommerceapi.viewmodel.OrderInputViewModel
import com.example.ecommerceapi.viewmodel.OrderOutputViewModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderService(
    val orderRepository: OrderRepository, val userRepository: UserRepository,
    val productRepository: ProductRepository, val cartItemRepository: CartItemRepository
) {

    fun placeOrder(userId: Long, orderInputViewModel: OrderInputViewModel): OrderOutputViewModel {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val productId = orderInputViewModel.productId
        val product = productRepository.findById(orderInputViewModel.productId)
            .orElseThrow { ProductNotFoundException(productId) }
        val order = orderInputViewModel.toOrder(product, user)
        user.orders += order
        return orderRepository.save(order).toOrderOutputViewModel()
    }

    fun orderCart(userId: Long): List<OrderOutputViewModel> {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val cartItemsId = user.cartItems.map { it.id }
        val ordersList = user.cartItems.map {
            placeOrder(userId, OrderInputViewModel(it.product.id, it.quantity))
        }
        user.cartItems = listOf()
        cartItemRepository.deleteAllById(cartItemsId)
        return ordersList
    }

    fun updateStatus(orderId: Long, orderStatus: OrderStatus): OrderOutputViewModel {
        val order = orderRepository.findById(orderId).orElseThrow { OrderNotFoundException(orderId) }
        order.status = orderStatus
        return orderRepository.save(order).toOrderOutputViewModel()
    }

    fun getOrderById(userId: Long, orderId: Long): OrderOutputViewModel {
        return orderRepository.findById(orderId).orElseThrow { OrderNotFoundException(orderId) }
            .toOrderOutputViewModel()
    }

    fun getOrders(
        userId: Long,
        status: OrderStatus?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        pageable: Pageable
    ): Page<OrderOutputViewModel> {
        val orders = orderRepository.findOrdersByFilters(pageable, userId, status, fromDate, toDate)
        return orders.map { it.toOrderOutputViewModel() }
    }

}
