package com.bookmarket.bookmarket.model

import com.bookmarket.bookmarket.enums.BookStatus
import com.bookmarket.bookmarket.enums.Errors
import com.bookmarket.bookmarket.exception.BadRequestException
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
) {

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.DELETADO || field == BookStatus.CANCELADO)
                throw BadRequestException(Errors.ML102.message.format(field), Errors.ML102.code)

            field = value

        }

    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: CustomerModel? = null,
        status: BookStatus?
    ) : this(id, name, price, customer) {
        this.status = status
    }

}