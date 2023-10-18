package com.example.ecommerceapi.controller

import com.example.ecommerceapi.service.UserService
import com.example.ecommerceapi.viewmodel.UserInputViewModel
import com.example.ecommerceapi.viewmodel.UserOutputViewModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userInputViewModel: UserInputViewModel): UserOutputViewModel {
        return userService.createUser(userInputViewModel)
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): UserOutputViewModel {
        return userService.getUserById(userId)
    }

    @GetMapping
    fun getAllUsers(): List<UserOutputViewModel> {
        return userService.getAllUsers()
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody updatedUser: UserInputViewModel): UserOutputViewModel {
        return userService.updateUser(userId, updatedUser)
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userId: Long) {
        userService.deleteUser(userId)
    }
}