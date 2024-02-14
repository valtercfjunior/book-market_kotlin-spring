package com.bookmarket.bookmarket.events.listener

import com.bookmarket.bookmarket.events.PurchaseEvent
import com.bookmarket.bookmarket.service.BookService
import com.bookmarket.bookmarket.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class UpdateSoldBookListener(
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }
}