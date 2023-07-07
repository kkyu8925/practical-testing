package sample.cafekiosk.spring.api.service.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*
import sample.cafekiosk.spring.domain.product.ProductType.*
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest
//@DataJpaTest
class OrderServiceTest @Autowired constructor(
    private val productRepository: ProductRepository,
    private val orderService: OrderService,
) {

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    fun createOrder() {
        // given
        val registeredDateTime = LocalDateTime.now()

        val product1 = createProduct("001", 1000)
        val product2 = createProduct("002", 3000)
        val product3 = createProduct("003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(listOf("001", "002"))

        // when
        val orderResponse = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(orderResponse.id).isNotNull()
        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 4000)
        assertThat(orderResponse.products).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("002", 3000)
            )
    }

    private fun createProduct(productNumber: String, price: Int): Product {
        return Product(
            type = HANDMADE,
            productNumber = productNumber,
            price = price,
            sellingStatus = SELLING,
            name = "메뉴 이름"
        )
    }
}
