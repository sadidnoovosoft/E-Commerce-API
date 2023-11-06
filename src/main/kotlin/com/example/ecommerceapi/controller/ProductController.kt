package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.service.ImageService
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import com.example.ecommerceapi.service.ProductService
import com.example.ecommerceapi.viewmodel.ProductViewModel
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/products")
class ProductController(
    val productService: ProductService,
    val imageService: ImageService
) {
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addProduct(
        @Valid @RequestPart product: Product,
        @RequestPart files: List<MultipartFile>
    ): String {
        val uploadFutures = files.map { imageService.uploadImage(it) }
        CompletableFuture.allOf(*uploadFutures.toTypedArray()).join()
        productService.addProduct(product)
        return "Product upload started"
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
    ): Page<ProductViewModel> {
        val sort = Sort.by(if (sortOrder == "desc") Sort.Direction.DESC else Sort.Direction.ASC, sortBy ?: "id")
        val pageable: Pageable = PageRequest.of(page ?: 0, size ?: 5, sort)
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
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateProduct(@RequestBody product: Product, @PathVariable productId: Long): ProductViewModel {
        return productService.updateProduct(product, productId)
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteProduct(@PathVariable productId: Long) {
        productService.deleteProduct(productId)
    }
}