package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.viewmodel.AddressViewModel
import com.example.ecommerceapi.viewmodel.UserViewModel
import org.springframework.stereotype.Service

@Service
class UserService {
    val users = mutableListOf<User>()
    private var userIdCounter: Long = 1

    fun createUser(user: User): UserViewModel {
        users.add(user.copy(id = userIdCounter++))
        return UserViewModel(userIdCounter - 1, user.firstName, user.lastName, user.password, user.email)
    }

    fun getUserById(userId: Long): User {
        return users.first { it.id == userId }
    }

    fun getAllUsers(pincode: String?, country: String?, state: String?, city: String?): List<UserViewModel> {
        val filteredUsers = users.filter { user ->
            (pincode == null || user.pincode == pincode) && (country == null || user.country == country)
            && (state == null || user.state == state) && (city == null || user.city == city)
        }
        return filteredUsers.map { user -> UserViewModel(user.id, user.firstName, user.lastName, user.password, user.email) }
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
        return UserViewModel(userId, newUser.firstName, newUser.lastName, newUser.password, newUser.email)
    }

    fun deleteUser(userId: Long) {
        users.remove(users.first { it.id == userId })
    }

    fun addUserAddress(userId: Long, address: AddressViewModel): AddressViewModel {
        val existingUser = users.first { it.id == userId }
        val userWithAddress = existingUser.copy(
            street = address.street,
            city = address.city,
            state = address.state,
            country = address.country,
            pincode = address.pincode
        )
        users.remove(existingUser)
        users.add(userWithAddress)
        return address
    }

    fun getUserAddress(userId: Long): AddressViewModel {
        val user = users.first { it.id == userId }
        return AddressViewModel(
            street = user.street, city = user.city, state = user.state, country = user.country, pincode = user.pincode
        )
    }

    fun updateUserAddress(userId: Long, address: AddressViewModel): AddressViewModel {
        val existingUser = users.first { it.id == userId }
        val userWithUpdatedAddress = existingUser.copy(
            street = address.street ?: existingUser.street,
            city = address.city ?: existingUser.city,
            state = address.state ?: existingUser.state,
            country = address.country ?: existingUser.country,
            pincode = address.pincode ?: existingUser.pincode
        )
        users.remove(existingUser)
        users.add(userWithUpdatedAddress)
        return AddressViewModel(
            userWithUpdatedAddress.street,
            userWithUpdatedAddress.city,
            userWithUpdatedAddress.state,
            userWithUpdatedAddress.country,
            userWithUpdatedAddress.pincode
        )
    }
}