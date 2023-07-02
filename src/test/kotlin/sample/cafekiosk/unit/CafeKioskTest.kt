package sample.cafekiosk.unit

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import sample.cafekiosk.unit.beverage.Americano
import sample.cafekiosk.unit.beverage.Latte

class CafeKioskTest {

    @Test
    fun add_manual_test() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        println(">>> 담긴 음료 수 : " + cafeKiosk.beverages.size)
        println(">>> 담긴 음료 : " + cafeKiosk.beverages.first().getName())
    }

    @Test
    fun add() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        assertThat(cafeKiosk.beverages).hasSize(1)
        assertThat(cafeKiosk.beverages.first().getName()).isEqualTo("아메리카노")
    }

    @Test
    fun addSeveralBeverages() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.add(americano, 2)

        assertThat(cafeKiosk.beverages[0]).isEqualTo(americano)
        assertThat(cafeKiosk.beverages[1]).isEqualTo(americano)
    }

    @Test
    fun addZeroBeverages() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        assertThatThrownBy {
            cafeKiosk.add(americano, 0)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.")
    }

    @Test
    fun remove() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.add(americano)
        assertThat(cafeKiosk.beverages).hasSize(1)

        cafeKiosk.remove(americano)
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    @Test
    fun clear() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.add(americano)
        cafeKiosk.add(latte)
        assertThat(cafeKiosk.beverages).hasSize(2)

        cafeKiosk.clear()
        assertThat(cafeKiosk.beverages).isEmpty()
    }
}
