package sample.cafekiosk.spring.api.service.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*
import sample.cafekiosk.spring.domain.product.ProductType
import sample.cafekiosk.spring.domain.product.ProductType.*

@ActiveProfiles("test")
@SpringBootTest
internal class ProductServiceTest @Autowired constructor(
    private val productService: ProductService,
    private val productRepository: ProductRepository,
) {

    @AfterEach
    fun tearDown() {
        productRepository.deleteAllInBatch()
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    @Test
    fun createProduct() {
        // given
        val product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000)
        productRepository.save(product)
        val request = ProductCreateRequest(
            type = HANDMADE,
            sellingStatus = SELLING,
            name = "카푸치노",
            price = 5000
        )

        // when
        val productResponse = productService.createProduct(request.createProductCreateServiceRequest())

        // then
        assertThat(productResponse)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("002", HANDMADE, SELLING, "카푸치노", 5000)
        val products = productRepository.findAll()
        assertThat(products).hasSize(2)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
                tuple("002", HANDMADE, SELLING, "카푸치노", 5000)
            )
    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    @Test
    fun createProductWhenProductsIsEmpty() {
        // given
        val request = ProductCreateRequest(
            type = HANDMADE,
            sellingStatus = SELLING,
            name = "카푸치노",
            price = 5000
        )

        // when
        val productResponse = productService.createProduct(request.createProductCreateServiceRequest())

        // then
        assertThat(productResponse)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("001", HANDMADE, SELLING, "카푸치노", 5000)
        val products = productRepository.findAll()
        assertThat(products).hasSize(1)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains(
                tuple("001", HANDMADE, SELLING, "카푸치노", 5000)
            )
    }

    private fun createProduct(
        productNumber: String, type: ProductType, sellingStatus: ProductSellingStatus, name: String, price: Int
    ): Product {
        return Product(
            productNumber = productNumber, type = type, sellingStatus = sellingStatus, name = name, price = price
        )
    }
}
