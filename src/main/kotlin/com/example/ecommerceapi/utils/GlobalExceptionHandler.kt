package com.example.ecommerceapi.utils

import com.example.ecommerceapi.viewmodel.ErrorResponseViewModel
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFound(e: ResourceNotFoundException): ErrorResponseViewModel {
        return ErrorResponseViewModel(
            message = e.message ?: "Resource not found"
        )
    }

    @ExceptionHandler(DuplicateResourceException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDuplicateResource(e: DuplicateResourceException): ErrorResponseViewModel {
        return ErrorResponseViewModel(
            message = e.message ?: "Resource already exists"
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        e.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as? FieldError)?.field
            val errorMessage = error.defaultMessage
            if (fieldName != null && errorMessage != null) {
                errors += fieldName to errorMessage
            }
        }
        return errors
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ErrorResponseViewModel {
        return ErrorResponseViewModel(
            message = e.message ?: "Something went wrong",
        )
    }
}