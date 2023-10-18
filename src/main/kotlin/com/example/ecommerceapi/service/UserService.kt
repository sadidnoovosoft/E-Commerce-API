package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.repository.UserRepository
import com.example.ecommerceapi.viewmodel.UserInputViewModel
import com.example.ecommerceapi.viewmodel.UserOutputViewModel
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun createUser(userInputViewModel: UserInputViewModel): UserOutputViewModel {
        return userRepository.save(userInputViewModel.toUser()).toUserOutputViewModel()
    }

    fun getUserById(userId: Long): UserOutputViewModel {
        return userRepository.findById(userId).get().toUserOutputViewModel()
    }

    fun getAllUsers(): List<UserOutputViewModel> {
        return userRepository.findAll().map(User::toUserOutputViewModel)
    }

    fun updateUser(userId: Long, userInputViewModel: UserInputViewModel): UserOutputViewModel {
        val existingUser = userRepository.findById(userId).get()
        existingUser.firstName = userInputViewModel.firstName
        existingUser.lastName = userInputViewModel.lastName
        existingUser.password = userInputViewModel.password
        existingUser.email = userInputViewModel.email
        return userRepository.save(existingUser).toUserOutputViewModel()
    }

    fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }
}