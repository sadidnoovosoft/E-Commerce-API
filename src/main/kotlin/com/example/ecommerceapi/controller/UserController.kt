package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.service.UserService
import com.example.ecommerceapi.viewmodel.AddressViewModel
import com.example.ecommerceapi.viewmodel.UserViewModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User): UserViewModel {
        return userService.createUser(user)
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): User {
        return userService.getUserById(userId)
    }

    @GetMapping
    fun getAllUsers(
        @RequestParam pincode: String?,
        @RequestParam country: String?,
        @RequestParam state: String?,
        @RequestParam city: String?,
    ): List<UserViewModel> {
        return userService.getAllUsers(pincode, country, state, city)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody updatedUser: User): UserViewModel {
        return userService.updateUser(userId, updatedUser)
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userId: Long) {
        userService.deleteUser(userId)
    }

    @PostMapping("/{userId}/address")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUserAddress(@PathVariable userId: Long, @RequestBody address: AddressViewModel): AddressViewModel {
        return userService.addUserAddress(userId, address)
    }

    @GetMapping("/{userId}/address")
    fun getUserAddress(@PathVariable userId: Long): AddressViewModel {
        return userService.getUserAddress(userId)
    }

    @PatchMapping("/{userId}/address")
    fun updateUserAddress(@PathVariable userId: Long, @RequestBody address: AddressViewModel): AddressViewModel {
        return userService.updateUserAddress(userId, address)
    }
}
