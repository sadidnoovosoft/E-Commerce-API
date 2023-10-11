package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Address
import org.springframework.stereotype.Service

@Service
class AddressService {
    val addresses = mutableListOf<Address>()
    private var addressIdCounter: Long = 1

    fun createAddress(address: Address): Address {
        addresses.add(address.copy(id = addressIdCounter))
        addressIdCounter += 1
        return address
    }

    fun getAddress(addressId: Long): Address {
        if (addresses.none { it.id == addressId }) {
            throw IllegalArgumentException(
                "No addresses with addressId: $addressId"
            )
        }
        return addresses.first { it.id == addressId }
    }

    fun getAddresses(userId: Long?): List<Address> {
        return if (userId != null) {
            addresses.filter { it.userId == userId }
        } else addresses
    }

    fun updateAddress(address: Address): Address {
        val addressIndex = addresses.indexOfFirst { it.id == address.id }
        if (addressIndex == -1) {
            throw NoSuchElementException("No address with addressId ${address.id}")
        }
        addresses[addressIndex] = address
        return addresses[addressIndex]
    }

    fun deleteAddress(addressId: Long) {
        val address = addresses.find { it.id == addressId }
            ?: throw NoSuchElementException(
                "Could not find address with addressId: $addressId"
            )
        addresses.remove(address)
    }
}