package com.bookmarket.bookmarket.controller.mapper

import com.bookmarket.bookmarket.controller.request.PostPurchaseRequest
import com.bookmarket.bookmarket.model.PurchaseModel
import com.bookmarket.bookmarket.service.BookService
import com.bookmarket.bookmarket.service.CustomerService
import org.springframework.stereotype.Component

@Component
class purchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getById(request.customerId)
        val books = bookService.findAllByIds(request.bookIds)

        return PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price },

            )
    }
}