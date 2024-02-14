package com.bookmarket.bookmarket.service

import com.bookmarket.bookmarket.events.PurchaseEvent
import com.bookmarket.bookmarket.model.PurchaseModel
import com.bookmarket.bookmarket.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
        private val purchaseRepository: PurchaseRepository,
        private val applicationEventPublisher: ApplicationEventPublisher
) {

        fun create(purchaseModel: PurchaseModel){
                purchaseRepository.save(purchaseModel)
                applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
        }

        fun update(purchaseModel: PurchaseModel) {
                purchaseRepository.save(purchaseModel)

        }
}
