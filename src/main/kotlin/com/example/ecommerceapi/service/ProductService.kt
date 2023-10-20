package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.viewmodel.ProductViewModel
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService(val productRepository: ProductRepository) {
    fun addProduct(product: Product): ProductViewModel {
        return productRepository.save(product).toProductViewModel()
    }

    fun getProducts(
        sortBy: String?,
        sortOrder: String?,
        minPrice: Double?,
        maxPrice: Double?,
        category: Category?,
        keywords: String?
    ): ProductsViewModel {
        val searchedProducts = if (keywords != null) {
            productRepository.findProductsByKeywords(keywords).map { it.id }
        } else null
        val sort = Sort.by(if (sortOrder == "desc") Sort.Direction.DESC else Sort.Direction.ASC, sortBy ?: "id")
        val products = productRepository.findProductsByFilters(sort, category, minPrice, maxPrice, searchedProducts)
            .map { it.toProductViewModel() }
        return ProductsViewModel(products.size, products)
    }

    fun getProductsByKeywords(keywords: String): ProductsViewModel {
        val searchedProducts = productRepository.findProductsByKeywords(keywords).map { it.toProductViewModel() }
        return ProductsViewModel(searchedProducts.size, searchedProducts)
    }

    fun getProductById(productId: Long): ProductViewModel {
        return productRepository.findById(productId).get().toProductViewModel()
    }

    fun updateProduct(newProduct: Product, productId: Long): ProductViewModel {
        val existingProduct = productRepository.findById(productId).get()
        existingProduct.name = newProduct.name
        existingProduct.price = newProduct.price
        existingProduct.description = newProduct.description
        existingProduct.category = newProduct.category
        return productRepository.save(existingProduct).toProductViewModel()
    }

    fun deleteProduct(productId: Long) {
        productRepository.deleteById(productId)
    }
}