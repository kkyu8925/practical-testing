package sample.cafekiosk.spring.domain.stock

import jakarta.persistence.Entity
import sample.cafekiosk.spring.domain.BaseEntity

@Entity
class Stock(
    val productNumber: String,
    var quantity: Int,
) : BaseEntity() {

    fun isQuantityLessThan(quantity: Int): Boolean {
        return this.quantity < quantity
    }

    fun deductQuantity(quantity: Int) {
        require(!isQuantityLessThan(quantity)) { "차감할 재고 수량이 없습니다." }
        this.quantity -= quantity
    }
}
