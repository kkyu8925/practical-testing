package sample.cafekiosk.spring.domain.stock

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class StockTest {

    @Test
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    fun isQuantityLessThan() {
        // given
        val stock = Stock("001", 1)
        val quantity = 2

        // when
        val result = stock.isQuantityLessThan(quantity)

        // then
        assertThat(result).isTrue()
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    fun deductQuantity() {
        // given
        val stock = Stock("001", 1)
        val quantity = 1

        // when
        stock.deductQuantity(quantity)

        // then
        assertThat(stock.quantity).isZero()
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    fun deductQuantity2() {
        // given
        val stock = Stock("001", 1)
        val quantity = 2

        // when // then
        assertThatThrownBy { stock.deductQuantity(quantity) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("차감할 재고 수량이 없습니다.")
    }

    @DisplayName("재고 차감 시나리오")
    @TestFactory
    fun stockDeductionDynamicTest(): Collection<DynamicTest> {
        // given
        val stock = Stock("001", 1)

        return listOf(
            DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.") {
                // given
                val quantity = 1

                // when
                stock.deductQuantity(quantity)

                // then
                assertThat(stock.quantity).isZero()
            },
            DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.") {
                // given
                val quantity = 1

                // when // then
                assertThatThrownBy { stock.deductQuantity(quantity) }
                    .isInstanceOf(java.lang.IllegalArgumentException::class.java)
                    .hasMessage("차감할 재고 수량이 없습니다.")
            }
        )
    }
}
