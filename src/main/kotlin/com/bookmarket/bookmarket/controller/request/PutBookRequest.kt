package com.bookmarket.bookmarket.controller.request

import com.bookmarket.bookmarket.enums.BookStatus
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

data class PutBookRequest (

    var name: String,
    var price: BigDecimal,

    )