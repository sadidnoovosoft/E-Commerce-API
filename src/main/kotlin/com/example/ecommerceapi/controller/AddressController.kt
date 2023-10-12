package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.Address
import com.example.ecommerceapi.service.AddressService
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
@RequestMapping("/users/{userId}/addresses")
class AddressController(
    private val addressService: AddressService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAddress(@PathVariable userId: Long, @RequestBody address: Address): Address {
        return addressService.createAddress(userId, address)
    }

    @GetMapping("/{addressId}")
    fun getAddress(@PathVariable userId: Long, @PathVariable addressId: Long): Address {
        return addressService.getAddress(addressId, userId)
    }

    @GetMapping
    fun getAddresses(@PathVariable userId: Long): List<Address> {
        return addressService.getAddresses(userId)
    }

    @PutMapping("/{addressId}")
    fun updateAddress(
        @PathVariable userId: Long,
        @PathVariable addressId: Long,
        @RequestBody address: Address,
    ): Address {
        return addressService.updateAddress(addressId, userId, address)
    }

    @DeleteMapping("/{addressId}")
    fun deleteAddress(@PathVariable userId: Long, @PathVariable addressId: Long) {
        return addressService.deleteAddress(addressId, userId)
    }
}