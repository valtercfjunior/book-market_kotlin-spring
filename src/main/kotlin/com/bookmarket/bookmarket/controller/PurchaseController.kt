package com.bookmarket.bookmarket.controller

import com.bookmarket.bookmarket.controller.mapper.purchaseMapper
import com.bookmarket.bookmarket.controller.request.PostPurchaseRequest
import com.bookmarket.bookmarket.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: purchaseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(request))

    }
}