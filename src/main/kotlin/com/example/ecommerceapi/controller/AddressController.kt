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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/address")
class AddressController(
    private val addressService: AddressService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAddress(@RequestBody address: Address): Address {
        return addressService.createAddress(address)
    }

    @GetMapping("/{addressId}")
    fun getAddress(@PathVariable addressId: Long): Address {
        return addressService.getAddress(addressId)
    }

    @GetMapping
    fun getAddresses(@RequestParam userId: Long?): List<Address> {
        return addressService.getAddresses(userId)
    }

    @PutMapping
    fun updateAddress(@RequestBody address: Address): Address {
        return addressService.updateAddress(address)
    }

    @DeleteMapping
    fun deleteAddress(@RequestParam addressId: Long) {
        return addressService.deleteAddress(addressId)
    }
}