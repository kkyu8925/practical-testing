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
