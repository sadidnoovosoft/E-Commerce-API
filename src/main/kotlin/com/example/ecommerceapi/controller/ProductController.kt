package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import com.example.ecommerceapi.service.ProductService
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
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun addProduct(@RequestBody product: Product): Product {
        return productService.addProduct(product)
    }

    @GetMapping
    fun getProducts(
        @RequestParam sortBy: String?,
        @RequestParam sortOrder: String?,
        @RequestParam minPrice: Double?,
        @RequestParam maxPrice: Double?,
        @RequestParam category: Category?
    ): ProductsViewModel {
        return productService.getProducts(sortBy, sortOrder, minPrice, maxPrice, category)
    }

    @GetMapping("/{productId}")
    fun getProductDetails(@PathVariable productId: Long): Product {
        return productService.getProductDetails(productId)
    }

    @GetMapping("/search")
    fun getProductByKeywords(@RequestParam keywords: String): ProductsViewModel {
        return productService.getProductsByKeywords(keywords)
    }

    @PutMapping
    fun updateProduct(@RequestBody product: Product): Product {
        return productService.updateProduct(product)
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long) {
        productService.deleteProduct(productId)
    }
}