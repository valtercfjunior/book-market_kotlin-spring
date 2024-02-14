package com.bookmarket.bookmarket.repository

import com.bookmarket.bookmarket.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository : CrudRepository<PurchaseModel, Int> {

}
