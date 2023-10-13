package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Address
import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.viewmodel.UserViewModel
import org.springframework.stereotype.Service

@Service
class UserService {
    val users = mutableListOf<User>()
    private var userIdCounter: Long = 1
    private var userAddressIdCounter: Long = 1

    fun createUser(user: User): UserViewModel {
        users.add(user.copy(id = userIdCounter++))
        return UserViewModel(userIdCounter - 1, user.firstName, user.lastName, user.email)
    }

    fun getUserById(userId: Long): User {
        return users.first { it.id == userId }
    }

    fun getAllUsers(pincode: String?, country: String?, state: String?, city: String?): List<UserViewModel> {
        return users.map { user -> UserViewModel(user.id, user.firstName, user.lastName, user.email) }
    }

    fun updateUser(userId: Long, updatedUser: User): UserViewModel {
        val existingUser = users.first { it.id == userId }
        val newUser = existingUser.copy(
            firstName = updatedUser.firstName,
            lastName = updatedUser.lastName,
            email = updatedUser.email,
            password = updatedUser.password
        )
        users.remove(existingUser)
        users.add(newUser)
        return UserViewModel(userId, newUser.firstName, newUser.lastName, newUser.email)
    }

    fun deleteUser(userId: Long) {
        users.remove(users.first { it.id == userId })
    }

    fun addUserAddress(userId: Long, address: Address): Address {
        val userAddresses = users.first { it.id == userId }.addresses
        userAddresses.add(address.copy(id = userAddressIdCounter++))
        return address
    }

    fun getUserAddresses(userId: Long): List<Address> {
        return users.first { it.id == userId }.addresses
    }

    fun updateUserAddress(userId: Long, addressId: Long, updatedAddress: Address): Address {
        val userAddresses = users.first { it.id == userId }.addresses
        userAddresses.remove(userAddresses.first { it.id == addressId })
        userAddresses.add(updatedAddress)
        return updatedAddress
    }

    fun deleteUserAddress(userId: Long, addressId: Long) {
        val userAddresses = users.first { it.id == userId }.addresses
        userAddresses.remove(userAddresses.first { it.id == addressId })
    }
}