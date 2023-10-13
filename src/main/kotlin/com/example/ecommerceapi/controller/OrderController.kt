package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Order
import com.example.ecommerceapi.model.OrderStatus
import com.example.ecommerceapi.service.OrderService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date
import kotlin.reflect.typeOf

@RestController
@RequestMapping("/users/{userId}/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun placeOrder(@PathVariable userId: Long, @RequestBody order: Order): Order {
        return orderService.placeOrder(order)
    }

    @PostMapping("/order-cart")
    fun orderCart(@PathVariable userId: Long): List<Order> {
        return orderService.orderCart(userId)
    }

    @PutMapping("/{orderId}/update-status/{orderStatus}")
    fun updateOrderStatus(
        @PathVariable userId: Long,
        @PathVariable orderId: Long,
        @PathVariable orderStatus: OrderStatus
    ): Order {
        return orderService.updateStatus(userId, orderId, orderStatus)
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable userId: Long, @PathVariable orderId: Long): Order {
        return orderService.getOrderById(userId, orderId)
    }

    @GetMapping
    fun getOrders(
        @PathVariable userId: Long,
        @RequestParam status: OrderStatus?,
        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") fromDate: Date?,
        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") toDate: Date?,
        @RequestParam sortBy: String?,
        @RequestParam sortOrder: String?,
    ): List<Order> {
        return orderService.getOrders(userId, status, fromDate, toDate, sortBy, sortOrder)
    }
}