package sample.cafekiosk.spring.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
internal class ProductRepositoryTest @Autowired constructor(
    private val productRepository: ProductRepository
) {
    private val product1 = Product(
        productNumber = "001",
        type = ProductType.HANDMADE,
        sellingStatus = SELLING,
        name = "아메리카노",
        price = 4000
    )
    private val product2 = Product(
        productNumber = "002",
        type = ProductType.HANDMADE,
        sellingStatus = HOLD,
        name = "카페라떼",
        price = 4500
    )
    private val product3 = Product(
        productNumber = "003",
        type = ProductType.HANDMADE,
        sellingStatus = STOP_SELLING,
        name = "팥빙수",
        price = 7000
    )

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    fun findAllBySellingStatusIn() {
        // given
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val products = productRepository.findAllBySellingStatusIn(listOf(SELLING, HOLD))

        // then
        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            )
    }

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    fun findAllByProductNumberIn() {
        // given
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val products = productRepository.findAllByProductNumberIn(listOf("001", "002"))

        // then
        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            )
    }
}
