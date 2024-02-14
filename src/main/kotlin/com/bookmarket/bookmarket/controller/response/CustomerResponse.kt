package com.bookmarket.bookmarket.controller.response

import com.bookmarket.bookmarket.enums.CustomerStatus

data class CustomerResponse (

    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus

)
