package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Address
import org.springframework.stereotype.Service

@Service
class AddressService {
    val addresses = mutableListOf<Address>()
    private var addressIdCounter: Long = 1

    fun createAddress(userId: Long, address: Address): Address {
        addresses.add(address.copy(id = addressIdCounter, userId = userId))
        addressIdCounter += 1
        return address
    }

    fun getAddress(addressId: Long, userId: Long): Address {
        return addresses.first { it.id == addressId && it.userId == userId }
    }

    fun getAddresses(userId: Long): List<Address> {
        return addresses.filter { it.userId == userId }
    }

    fun updateAddress(addressId: Long, userId: Long, newAddress: Address): Address {
        val oldAddress = addresses.first { it.id == addressId && it.userId == userId }
        addresses.remove(oldAddress)
        addresses.add(newAddress.copy(id = addressId, userId = userId))
        return newAddress
    }

    fun deleteAddress(addressId: Long, userId: Long) {
        val address = addresses.find { it.id == addressId && it.userId == userId }
        addresses.remove(address)
    }
}