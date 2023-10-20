package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long> {
    @Query(
        """SELECT p FROM Product p
                WHERE (:searchedProducts IS NULL OR p.id IN (:searchedProducts))
                AND (:category IS NULL OR p.category = :category) 
                AND (:minPrice IS NULL OR p.price >= :minPrice) 
                AND (:maxPrice IS NULL OR p.price <= :maxPrice)"""
    )
    fun findProductsByFilters(
        pageable: Pageable,
        category: Category?,
        minPrice: Double?,
        maxPrice: Double?,
        searchedProducts: List<Long>?
    ): Page<Product>

    @Query(
        """SELECT * FROM product
        WHERE (
            SELECT COUNT(*)
            FROM unnest(string_to_array(:keywords, ' ')) AS word
            WHERE product.name ILIKE ('%' || word || '%')
            OR product.description ILIKE ('%' || word || '%')
        ) > 0
        """,
        nativeQuery = true
    )
    fun findProductsByKeywords(keywords: String): List<Product>

}