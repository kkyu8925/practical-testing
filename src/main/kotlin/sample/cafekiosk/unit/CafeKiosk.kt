package sample.cafekiosk.unit

import sample.cafekiosk.unit.beverage.Beverage
import sample.cafekiosk.unit.order.Order
import java.time.LocalDateTime

data class CafeKiosk(
    val beverages: MutableList<Beverage> = mutableListOf()
) {
    fun add(beverage: Beverage) {
        beverages.add(beverage)
    }

    fun add(beverage: Beverage, count: Int) {
        require(count > 0) {
            "음료는 1잔 이상 주문하실 수 있습니다."
        }

        for (i in 0..count) {
            beverages.add(beverage)
        }
    }

    fun remove(beverage: Beverage) {
        beverages.remove(beverage)
    }

    fun clear() {
        beverages.clear()
    }

    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (beverage in beverages) {
            totalPrice += beverage.getPrice()
        }
        return totalPrice
    }

    fun createOrder(): Order {
        return Order(LocalDateTime.now(), beverages)
    }
}
