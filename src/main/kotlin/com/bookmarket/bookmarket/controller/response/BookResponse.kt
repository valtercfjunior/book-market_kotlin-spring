package com.bookmarket.bookmarket.controller.response

import com.bookmarket.bookmarket.enums.BookStatus
import com.bookmarket.bookmarket.model.CustomerModel
import java.math.BigDecimal

data class BookResponse(

    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: CustomerModel? = null,
    var status: BookStatus? = null,

    )
