package com.bookmarket.bookmarket.controller.request

import com.bookmarket.bookmarket.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @field:Email(message = "E-mail deve ser válido")
    @EmailAvailable(message = "E-mail em uso")
    var email: String,


    @field:NotEmpty(message = "A senha deve ser informada")
    var password: String
)