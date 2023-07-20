package sample.cafekiosk.spring.api.service.order

import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import sample.cafekiosk.spring.IntegrationTestSupport
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository
import sample.cafekiosk.spring.domain.order.Order
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.order.OrderStatus
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*
import sample.cafekiosk.spring.domain.product.ProductType
import sample.cafekiosk.spring.domain.product.ProductType.*
import java.time.LocalDate
import java.time.LocalDateTime

class OrderStatisticsServiceTest @Autowired constructor(
    private val orderStatisticsService: OrderStatisticsService,
    private val orderProductRepository: OrderProductRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val mailSendHistoryRepository: MailSendHistoryRepository,
) : IntegrationTestSupport() {

    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        mailSendHistoryRepository.deleteAllInBatch()
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    fun sendOrderStatisticsMail() {
        // given
        val now = LocalDateTime.of(2023, 3, 5, 0, 0)

        val product1 = createProduct(HANDMADE, "111", 1000)
        val product2 = createProduct(HANDMADE, "222", 2000)
        val product3 = createProduct(HANDMADE, "333", 3000)
        val products = listOf(product1, product2, product3)

        createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 4, 23, 59, 59), products)
        createPaymentCompletedOrder(now, products)
        createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 5, 23, 59, 59), products)
        createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 6, 0, 0), products)

        // stubbing
        every {
            mailSendClient.sendEmail(allAny(), allAny(), allAny(), allAny())
        } returns true

        // when
        val result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com")

        // then
        assertThat(result).isTrue()

        val histories = mailSendHistoryRepository.findAll()
        assertThat(histories)
            .hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 12000원입니다.")
    }

    private fun createPaymentCompletedOrder(now: LocalDateTime, products: List<Product>): Order {
        val order = Order(
            products = products, registeredDateTime = now
        )
        order.updateOrderStatus(OrderStatus.PAYMENT_COMPLETED)
        return orderRepository.save(order)
    }

    private fun createProduct(type: ProductType, productNumber: String, price: Int): Product {
        val product = Product(
            type = type, productNumber = productNumber, price = price, sellingStatus = SELLING, name = "메뉴 이름"
        )
        return productRepository.save(product)
    }
}
