package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.Order
import com.example.ecommerceapi.model.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface OrderRepository : JpaRepository<Order, Long> {
    @Query(
        """SELECT o FROM Order o 
                WHERE (o.user.id = :userId) 
                AND (:status IS NULL OR o.status = :status) 
                AND (o.date >= COALESCE(:fromDate, o.date)) 
                AND (o.date <= COALESCE(:toDate, o.date))"""
    )
    fun findOrdersByFilters(
        pageable: Pageable, userId: Long, status: OrderStatus?, fromDate: LocalDate?, toDate: LocalDate?
    ): Page<Order>
}