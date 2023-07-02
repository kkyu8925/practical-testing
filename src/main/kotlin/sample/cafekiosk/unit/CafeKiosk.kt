package sample.cafekiosk.unit

import sample.cafekiosk.unit.beverage.Beverage
import sample.cafekiosk.unit.order.Order
import java.time.LocalDateTime
import java.time.LocalTime


data class CafeKiosk(
    val beverages: MutableList<Beverage> = mutableListOf()
) {
    private val shopOpenTime = LocalTime.of(10, 0)
    private val shopCloseTime = LocalTime.of(22, 0)

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
        val currentDateTime = LocalDateTime.now()
        val currentTime = currentDateTime.toLocalTime()

        require(!(currentTime.isBefore(shopOpenTime) || currentTime.isAfter(shopCloseTime))) {
            "주문 시간이 아닙니다. 관리자에게 문의하세요."
        }

        return Order(currentDateTime, beverages)
    }

    fun createOrder(currentDateTime: LocalDateTime): Order {
        val currentTime = currentDateTime.toLocalTime()

        require(!(currentTime.isBefore(shopOpenTime) || currentTime.isAfter(shopCloseTime))) {
            "주문 시간이 아닙니다. 관리자에게 문의하세요."
        }

        return Order(currentDateTime, beverages)
    }
}
