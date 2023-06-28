package sample.cafekiosk.unit

import org.junit.jupiter.api.Test
import sample.cafekiosk.unit.beverage.Americano

class CafeKioskTest {

    @Test
    fun add() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        println(">>> 담긴 음료 수 : " + cafeKiosk.beverages.size)
        println(">>> 담긴 음료 : " + cafeKiosk.beverages.first().getName())
    }
}
