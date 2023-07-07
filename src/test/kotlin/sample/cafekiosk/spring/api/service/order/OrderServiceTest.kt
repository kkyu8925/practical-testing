package sample.cafekiosk.spring.api.service.order

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*
import sample.cafekiosk.spring.domain.product.ProductType
import sample.cafekiosk.spring.domain.product.ProductType.*
import sample.cafekiosk.spring.domain.stock.Stock
import sample.cafekiosk.spring.domain.stock.StockRepository
import java.time.LocalDateTime

@ActiveProfiles("test")
//@Transactional
@SpringBootTest
class OrderServiceTest @Autowired constructor(
    private val productRepository: ProductRepository,
    private val orderService: OrderService,
    private val orderProductRepository: OrderProductRepository,
    private val orderRepository: OrderRepository,
    private val stockRepository: StockRepository
) {

    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
        stockRepository.deleteAllInBatch()
    }

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

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    fun createOrderWithDuplicateProductNumbers() {
        // given
        val registeredDateTime = LocalDateTime.now()

        val product1 = createProduct("001", 1000)
        val product2 = createProduct("002", 3000)
        val product3 = createProduct("003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val request = OrderCreateRequest(listOf("001", "001"))

        // when
        val orderResponse = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(orderResponse.id).isNotNull()
        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 2000)
        assertThat(orderResponse.products).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000)
            )
    }

    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    fun createOrderWithStock() {
        // given
        val registeredDateTime = LocalDateTime.now()

        val product1 = createProduct(BOTTLE, "001", 1000)
        val product2 = createProduct(BAKERY, "002", 3000)
        val product3 = createProduct(HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock("001", 2)
        val stock2 = Stock("002", 2)
        stockRepository.saveAll(listOf(stock1, stock2))

        val request = OrderCreateRequest(listOf("001", "001", "002", "003"))

        // when
        val orderResponse = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(orderResponse.id).isNotNull()
        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 10000)
        assertThat(orderResponse.products).hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
                tuple("002", 3000),
                tuple("003", 5000)
            )

        val stocks = stockRepository.findAll()
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1)
            )
    }

    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    fun createOrderWithNoStock() {
        // given
        val registeredDateTime = LocalDateTime.now()

        val product1 = createProduct(BOTTLE, "001", 1000)
        val product2 = createProduct(BAKERY, "002", 3000)
        val product3 = createProduct(HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock("001", 2)
        val stock2 = Stock("002", 2)
        stock1.deductQuantity(1) // todo
        stockRepository.saveAll(listOf(stock1, stock2))

        val request = OrderCreateRequest(listOf("001", "001", "002", "003"))

        // when // then
        assertThatThrownBy { orderService.createOrder(request, registeredDateTime) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고가 부족한 상품이 있습니다.")
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

    private fun createProduct(type: ProductType, productNumber: String, price: Int): Product {
        return Product(
            type = type,
            productNumber = productNumber,
            price = price,
            sellingStatus = SELLING,
            name = "메뉴 이름"
        )
    }
}
