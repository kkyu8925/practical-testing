package sample.cafekiosk.spring.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType
import java.time.LocalDateTime

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    fun calculateTotalPrice() {
        // given
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000)
        )

        // when
        val order = Order(products, LocalDateTime.now())

        // then
        assertThat(order.totalPrice).isEqualTo(3000)
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    @Test
    fun init() {
        // given
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000)
        )

        // when
        val order = Order(products, LocalDateTime.now())

        // then
        assertThat(order.orderStatus).isEqualByComparingTo(OrderStatus.INIT)
    }

    @DisplayName("주문 생성 시 주문 등록 시간을 기록한다.")
    @Test
    fun registeredDateTime() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000)
        )

        // when
        val order = Order(products, registeredDateTime)

        // then
        assertThat(order.registeredDateTime).isEqualTo(registeredDateTime)
    }

    private fun createProduct(productNumber: String, price: Int): Product {
        return Product(
            type = ProductType.HANDMADE,
            productNumber = productNumber,
            price = price,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "메뉴 이름"
        )
    }
}
