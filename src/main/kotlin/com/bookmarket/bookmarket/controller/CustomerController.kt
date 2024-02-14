package com.bookmarket.bookmarket.controller

import com.bookmarket.bookmarket.controller.request.PostCustomerRequest
import com.bookmarket.bookmarket.controller.request.PutCustomerRequest
import com.bookmarket.bookmarket.controller.response.CustomerResponse
import com.bookmarket.bookmarket.extension.toCustomerModel
import com.bookmarket.bookmarket.extension.toResponse
import com.bookmarket.bookmarket.security.UserCanOnlyAccessTheirOwnResource
import com.bookmarket.bookmarket.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("customer")
class CustomerController(
    val customerService: CustomerService,
) {


    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerResponse> {
      return customerService.getAll(name).map {it.toResponse()}
    }

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun getCustomer(@PathVariable id: Int): CustomerResponse {
        return customerService.getById(id).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable @Valid id: Int, @RequestBody customer: PutCustomerRequest) {
        val customerSaved = customerService.getById(id)
        return customerService.update(customer.toCustomerModel(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id)
    }

}
