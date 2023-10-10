package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getCategories(
        @RequestParam sortBy: String?,
        @RequestParam sortOrder: String?
    ): List<Category> {
        return categoryService.getCategories(sortBy, sortOrder)
    }

    @PostMapping
    fun createCategory(@RequestBody category: Category): Category {
        return categoryService.createCategory(category)
    }
}