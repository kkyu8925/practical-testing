package sample.cafekiosk.spring.domain.product

import jakarta.persistence.*
import sample.cafekiosk.spring.domain.BaseEntity

@Entity
class Product(

    @Enumerated(EnumType.STRING)
    val type: ProductType,

    val productNumber: String,

    @Enumerated(EnumType.STRING)
    val sellingStatus: ProductSellingStatus,

    val name: String,

    val price: Int,
) : BaseEntity()
