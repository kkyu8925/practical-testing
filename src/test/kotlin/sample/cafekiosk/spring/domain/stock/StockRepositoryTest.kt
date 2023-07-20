package sample.cafekiosk.spring.domain.stock

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import sample.cafekiosk.spring.IntegrationTestSupport
import java.util.List

@Transactional
internal class StockRepositoryTest @Autowired constructor(
    private val stockRepository: StockRepository
) : IntegrationTestSupport() {

    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    fun findAllByProductNumberIn() {
        // given
        val stock1 = Stock("001", 1)
        val stock2 = Stock("002", 2)
        val stock3 = Stock("003", 3)
        stockRepository.saveAll(List.of(stock1, stock2, stock3))

        // when
        val stocks = stockRepository.findAllByProductNumberIn(listOf("001", "002"))

        // then
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 1),
                tuple("002", 2)
            )
    }
}
