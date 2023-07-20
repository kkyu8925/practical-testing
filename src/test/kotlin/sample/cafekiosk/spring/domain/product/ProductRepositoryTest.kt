package sample.cafekiosk.spring.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import sample.cafekiosk.spring.IntegrationTestSupport
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*
import sample.cafekiosk.spring.domain.product.ProductType.*

@Transactional
internal class ProductRepositoryTest @Autowired constructor(
    private val productRepository: ProductRepository
) : IntegrationTestSupport() {

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    fun findAllBySellingStatusIn() {
        // given
        val product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000)
        val product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500)
        val product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000)
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
        val product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000)
        val product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500)
        val product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000)
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

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    @Test
    fun findLatestProductNumber() {
        // given
        val targetProductNumber = "003"

        val product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000)
        val product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500)
        val product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val latestProductNumber = productRepository.findLatestProductNumber()

        // then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber)
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    fun findLatestProductNumberWhenProductIsEmpty() {
        // when
        val latestProductNumber = productRepository.findLatestProductNumber()

        // then
        assertThat(latestProductNumber).isNull()
    }

    private fun createProduct(
        productNumber: String,
        type: ProductType,
        sellingStatus: ProductSellingStatus,
        name: String,
        price: Int
    ): Product {
        return Product(
            productNumber = productNumber,
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price
        )
    }
}
