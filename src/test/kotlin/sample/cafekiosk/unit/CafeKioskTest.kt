package sample.cafekiosk.unit

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import sample.cafekiosk.unit.beverage.Americano
import sample.cafekiosk.unit.beverage.Latte
import java.time.LocalDateTime

class CafeKioskTest {

    @Test
    fun add_manual_test() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        println(">>> 담긴 음료 수 : " + cafeKiosk.beverages.size)
        println(">>> 담긴 음료 : " + cafeKiosk.beverages.first().getName())
    }

    @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
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
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessage("음료는 1잔 이상 주문하실 수 있습니다.")
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

    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    @Test
    fun calculateTotalPrice() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.add(americano)
        cafeKiosk.add(latte)

        // when
        val totalPrice = cafeKiosk.calculateTotalPrice()

        // then
        assertThat(totalPrice).isEqualTo(8500)
    }

    @Test
    fun createOrder() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        cafeKiosk.add(americano)

        val order = cafeKiosk.createOrder()

        assertThat(order.beverages).hasSize(1)
        assertThat(order.beverages.first().getName()).isEqualTo("아메리카노")
    }

    @Test
    fun createOrderWithCurrentTime() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        cafeKiosk.add(americano)

        val order = cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 10, 0))

        assertThat(order.beverages).hasSize(1)
        assertThat(order.beverages.first().getName()).isEqualTo("아메리카노")
    }

    @Test
    fun createOrderOutsideOpenTime() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        cafeKiosk.add(americano)

        assertThatThrownBy {
            cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9, 59))
        }.isInstanceOf(java.lang.IllegalArgumentException::class.java).hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.")
    }
}
