package sample.cafekiosk.spring.domain.orderproduct

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import sample.cafekiosk.spring.domain.BaseEntity
import sample.cafekiosk.spring.domain.order.Order
import sample.cafekiosk.spring.domain.product.Product

@Entity
class OrderProduct(

    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
) : BaseEntity()
