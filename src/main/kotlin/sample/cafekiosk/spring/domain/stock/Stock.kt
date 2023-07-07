package sample.cafekiosk.spring.domain.stock

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import sample.cafekiosk.spring.domain.BaseEntity

@Entity
class Stock(
    val productNumber: String,
    var quantity: Int,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
        protected set

    fun isQuantityLessThan(quantity: Int): Boolean {
        return this.quantity < quantity
    }

    fun deductQuantity(quantity: Int) {
        require(!isQuantityLessThan(quantity)) { "차감할 재고 수량이 없습니다." }
        this.quantity -= quantity
    }
}
