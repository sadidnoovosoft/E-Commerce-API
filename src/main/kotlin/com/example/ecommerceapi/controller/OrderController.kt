package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.OrderStatus
import com.example.ecommerceapi.service.OrderService
import com.example.ecommerceapi.viewmodel.OrderInputViewModel
import com.example.ecommerceapi.viewmodel.OrderOutputViewModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/users/{userId}/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun placeOrder(
        @PathVariable userId: Long, @RequestBody orderInputViewModel: OrderInputViewModel
    ): OrderOutputViewModel {
        return orderService.placeOrder(userId, orderInputViewModel)
    }

    @PostMapping("/cart")
    fun orderCart(@PathVariable userId: Long): List<OrderOutputViewModel> {
        return orderService.orderCart(userId)
    }

    @PutMapping("/{orderId}/status/{orderStatus}")
    fun updateOrderStatus(
        @PathVariable userId: Long, @PathVariable orderId: Long, @PathVariable orderStatus: OrderStatus
    ): OrderOutputViewModel {
        return orderService.updateStatus(orderId, orderStatus)
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable userId: Long, @PathVariable orderId: Long): OrderOutputViewModel {
        return orderService.getOrderById(userId, orderId)
    }

    @GetMapping
    fun getOrders(
        @PathVariable userId: Long,
        @RequestParam status: OrderStatus?,
        @RequestParam("from") fromDate: LocalDate?,
        @RequestParam("to") toDate: LocalDate?,
        @RequestParam sortBy: String?,
        @RequestParam sortOrder: String?,
        @RequestParam page: Int?,
        @RequestParam size: Int?
    ): Page<OrderOutputViewModel> {
        val sort = Sort.by(if (sortOrder == "asc") Sort.Direction.ASC else Sort.Direction.DESC, sortBy ?: "date")
        val pageable: Pageable = PageRequest.of(maxOf(page ?: 1, 1) - 1, size ?: 5, sort)
        return orderService.getOrders(userId, status, fromDate, toDate, pageable)
    }
}