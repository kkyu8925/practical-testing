package sample.cafekiosk.spring.domain.orderproduct

import jakarta.persistence.*
import sample.cafekiosk.spring.domain.BaseEntity
import sample.cafekiosk.spring.domain.order.Order
import sample.cafekiosk.spring.domain.product.Product

@Entity
class OrderProduct(

    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
