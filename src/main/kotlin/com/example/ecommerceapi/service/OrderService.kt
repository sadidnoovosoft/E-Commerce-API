package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Order
import com.example.ecommerceapi.model.OrderStatus
import org.springframework.stereotype.Service
import java.util.Date

@Service
class OrderService(
    private val productService: ProductService, private val cartService: CartService
) {
    val orders = mutableListOf<Order>()
    private var orderIdCounter: Long = 1

    fun placeOrder(order: Order): Order {
        val orderToBePlaced = order.copy(
            id = orderIdCounter++,
            price = order.quantity * productService.getProductDetails(order.productId).price,
            status = OrderStatus.CREATED,
        )
        orders.add(orderToBePlaced)
        return orderToBePlaced
    }

    fun orderCart(userId: Long): List<Order> {
        val cartItems = cartService.getCartItems(userId).cartItems
        val cartOrders = cartItems.map { cartItem ->
            Order(
                id = orderIdCounter++,
                userId = userId,
                productId = cartItem.productId,
                quantity = cartItem.quantity,
                price = cartItem.price,
                status = OrderStatus.CREATED,
            )
        }
        orders += cartOrders
        return cartOrders
    }

    fun updateStatus(userId: Long, orderId: Long, orderStatus: OrderStatus): Order {
        val existingOrder = orders.first { it.id == orderId && it.userId == userId }
        val orderWithUpdatedStatus = existingOrder.copy(status = orderStatus)
        orders.remove(existingOrder)
        orders.add(orderWithUpdatedStatus)
        return orderWithUpdatedStatus
    }

    fun getOrderById(userId: Long, orderId: Long): Order {
        return orders.first { it.id == orderId && it.userId == userId }
    }

    fun getOrders(
        userId: Long,
        status: OrderStatus?,
        fromDate: Date?,
        toDate: Date?,
        sortBy: String?,
        sortOrder: String?,
    ): List<Order> {
        val filteredOrders = orders.asSequence()
            .filter { it.userId == userId }
            .filter { status == null || it.status == status }
            .filter { (fromDate == null || it.date >= fromDate) && (toDate == null || it.date <= toDate) }
            .toList()
        val sortedOrders = when (sortBy) {
            "quantity" -> filteredOrders.sortedByDescending { it.quantity }
            "price" -> filteredOrders.sortedByDescending { it.price }
            else -> filteredOrders.sortedByDescending { it.date }
        }
        return if (sortOrder == "asc") sortedOrders.reversed() else sortedOrders
    }
}