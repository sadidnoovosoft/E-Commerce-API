package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import com.example.ecommerceapi.service.ProductService
import com.example.ecommerceapi.viewmodel.ProductViewModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
class ProductController(val productService: ProductService) {

    @PostMapping
    fun addProduct(@RequestBody product: Product): ProductViewModel {
        return productService.addProduct(product)
    }

    @GetMapping
    fun getProducts(
        @RequestParam sortBy: String?,
        @RequestParam sortOrder: String?,
        @RequestParam page: Int?,
        @RequestParam size: Int?,
        @RequestParam minPrice: Double?,
        @RequestParam maxPrice: Double?,
        @RequestParam category: Category?,
        @RequestParam keywords: String?
    ): Page<Product> {
        val sort = Sort.by(if (sortOrder == "desc") Sort.Direction.DESC else Sort.Direction.ASC, sortBy ?: "id")
        val pageable: Pageable = PageRequest.of(maxOf(page ?: 1, 1) - 1, size ?: 5, sort)
        return productService.getProducts(pageable, minPrice, maxPrice, category, keywords)
    }

    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long): ProductViewModel {
        return productService.getProductById(productId)
    }

    @GetMapping("/search")
    fun getProductByKeywords(@RequestParam keywords: String): ProductsViewModel {
        return productService.getProductsByKeywords(keywords)
    }

    @PutMapping("/{productId}")
    fun updateProduct(@RequestBody product: Product, @PathVariable productId: Long): ProductViewModel {
        return productService.updateProduct(product, productId)
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long) {
        productService.deleteProduct(productId)
    }
}