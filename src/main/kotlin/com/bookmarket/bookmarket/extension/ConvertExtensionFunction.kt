package com.bookmarket.bookmarket.extension

import com.bookmarket.bookmarket.controller.request.PostBookRequest
import com.bookmarket.bookmarket.controller.request.PostCustomerRequest
import com.bookmarket.bookmarket.controller.request.PutBookRequest
import com.bookmarket.bookmarket.controller.request.PutCustomerRequest
import com.bookmarket.bookmarket.controller.response.BookResponse
import com.bookmarket.bookmarket.controller.response.CustomerResponse
import com.bookmarket.bookmarket.enums.BookStatus
import com.bookmarket.bookmarket.enums.CustomerStatus
import com.bookmarket.bookmarket.model.BookModel
import com.bookmarket.bookmarket.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
        name = this.name,
        email = this.email,
        status = CustomerStatus.ATIVO,
        password = this.password
    )
}

fun PutCustomerRequest.toCustomerModel(prevCustomer: CustomerModel): CustomerModel {
    return CustomerModel(
        id = prevCustomer.id,
        name = this.name,
        email = this.email,
        status = prevCustomer.status,
        password = prevCustomer.password
    )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PutBookRequest.toBookModel(prevBook: BookModel): BookModel {
    return BookModel(
        id = prevBook.id,
        name = this.name,
        price = this.price,
        status = prevBook.status,
        customer = prevBook
            .customer
    )
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status,
    )
}


fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )

}