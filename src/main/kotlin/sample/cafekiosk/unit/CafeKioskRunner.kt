package sample.cafekiosk.unit

import sample.cafekiosk.unit.beverage.Americano
import sample.cafekiosk.unit.beverage.Latte

fun main() {
    val cafeKiosk = CafeKiosk()
    cafeKiosk.add(Americano())
    println(">>> 아메리카노 추가")
    cafeKiosk.add(Latte())
    println(">>> 라떼 추가")

    val totalPrice = cafeKiosk.calculateTotalPrice()
    println("총 주문가격 : $totalPrice")
}
