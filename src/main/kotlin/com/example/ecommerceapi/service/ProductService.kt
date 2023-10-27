package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.utils.ProductNotFoundException
import com.example.ecommerceapi.viewmodel.ProductViewModel
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(val productRepository: ProductRepository) {
    fun addProduct(product: Product): ProductViewModel {
        return productRepository.save(product).toProductViewModel()
    }

    fun getProducts(
        pageable: Pageable, minPrice: Double?, maxPrice: Double?, category: Category?, keywords: String?
    ): Page<Product> {
        val searchedProducts = keywords?.takeIf { it.isNotBlank() }?.let {
            productRepository.findProductsByKeywords(keywords).map { it.id }
        }
        return productRepository.findProductsByFilters(pageable, category, minPrice, maxPrice, searchedProducts)
    }

    fun getProductsByKeywords(keywords: String): ProductsViewModel {
        val searchedProducts = productRepository.findProductsByKeywords(keywords.trim()).map { it.toProductViewModel() }
        return ProductsViewModel(searchedProducts.size, searchedProducts)
    }

    fun getProductById(productId: Long): ProductViewModel {
        return productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }
            .toProductViewModel()
    }

    fun updateProduct(newProduct: Product, productId: Long): ProductViewModel {
        val existingProduct = productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }
        existingProduct.name = newProduct.name
        existingProduct.price = newProduct.price
        existingProduct.description = newProduct.description
        existingProduct.category = newProduct.category
        return productRepository.save(existingProduct).toProductViewModel()
    }

    fun deleteProduct(productId: Long) {
        val product = productRepository.findById(productId).orElseThrow { ProductNotFoundException(productId) }
        productRepository.delete(product)
    }
}