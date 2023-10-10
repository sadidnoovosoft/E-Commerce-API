package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.model.ProductList
import com.example.ecommerceapi.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun addProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val addedProduct = productService.addProduct(product)
        return ResponseEntity(addedProduct, HttpStatus.CREATED)
    }

    @GetMapping
    fun getProducts(
        @RequestParam("sortBy") sortBy: String?,
        @RequestParam("sortOrder") sortOrder: String?,
        @RequestParam("minPrice") minPrice: Double?,
        @RequestParam("maxPrice") maxPrice: Double?,
        @RequestParam("categoryId") categoryId: Long?
    ): ResponseEntity<ProductList> {
        val productList = productService.getProducts(sortBy, sortOrder, minPrice, maxPrice, categoryId)
        return ResponseEntity(productList, HttpStatus.OK)
    }

    @GetMapping("/{productId}")
    fun getProductDetails(@PathVariable productId: Long): ResponseEntity<Product> {
        val product = productService.getProductDetails(productId)
        return ResponseEntity(product, HttpStatus.OK)
    }

    @PutMapping
    fun updateProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(product)
        return ResponseEntity(updatedProduct, HttpStatus.OK)
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long) {
        productService.deleteProduct(productId)
    }
}