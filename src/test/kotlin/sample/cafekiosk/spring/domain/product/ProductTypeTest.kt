package sample.cafekiosk.spring.domain.product

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    fun containsStockType() {
        // given
        val givenType = ProductType.HANDMADE

        // when
        val result = ProductType.containsStockType(givenType)

        // then
        assertThat(result).isFalse()
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    fun containsStockType2() {
        // given
        val givenType = ProductType.BAKERY

        // when
        val result = ProductType.containsStockType(givenType)

        // then
        assertThat(result).isTrue()
    }
}
