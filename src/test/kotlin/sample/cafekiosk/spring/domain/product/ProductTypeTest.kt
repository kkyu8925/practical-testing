package sample.cafekiosk.spring.domain.product

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

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

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource("HANDMADE,false", "BOTTLE,true", "BAKERY,true")
    @ParameterizedTest
    fun containsStockType4(productType: ProductType, expected: Boolean) {
        // when
        val result = ProductType.containsStockType(productType)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    fun containsStockType5(productType: ProductType, expected: Boolean) {
        // when
        val result = ProductType.containsStockType(productType)

        // then
        assertThat(result).isEqualTo(expected)
    }

    companion object {

        @JvmStatic
        private fun provideProductTypesForCheckingStockType(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
            )
        }
    }
}
