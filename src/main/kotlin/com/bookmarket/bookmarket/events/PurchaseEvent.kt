package com.bookmarket.bookmarket.events

import com.bookmarket.bookmarket.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,
    val purchaseModel: PurchaseModel
): ApplicationEvent(source)