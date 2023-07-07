package sample.cafekiosk.spring.api.service.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.api.service.order.response.OrderResponse
import sample.cafekiosk.spring.domain.order.Order
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductType
import sample.cafekiosk.spring.domain.stock.Stock
import sample.cafekiosk.spring.domain.stock.StockRepository
import java.time.LocalDateTime

@Transactional
@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val stockRepository: StockRepository
) {

    fun createOrder(request: OrderCreateRequest, registeredDateTime: LocalDateTime): OrderResponse {
        val productNumbers = request.productNumbers
        val products = findProductsBy(productNumbers)

        deductStockQuantities(products);

        val order = Order(products = products, registeredDateTime = registeredDateTime)
        val savedOrder = orderRepository.save(order)
        return OrderResponse.of(savedOrder)
    }

    private fun deductStockQuantities(products: List<Product>) {
        val stockProductNumbers = extractStockProductNumbers(products)

        val stockMap = createStockMapBy(stockProductNumbers)
        val productCountingMap = createCountingMapBy(stockProductNumbers)

        for (stockProductNumber in HashSet(stockProductNumbers)) {
            val stock = checkNotNull(stockMap[stockProductNumber])
            val count = checkNotNull(productCountingMap[stockProductNumber])

            require(!stock.isQuantityLessThan(count)) { "재고가 부족한 상품이 있습니다." }
            stock.deductQuantity(count)
        }
    }

    private fun findProductsBy(productNumbers: List<String>): List<Product> {
        val products = productRepository.findAllByProductNumberIn(productNumbers)
        val productMap = products.associateBy { it.productNumber }

        return productNumbers.map { key -> checkNotNull(productMap[key]) }
    }

    private fun extractStockProductNumbers(products: List<Product>): List<String> {
        return products.filter {
            ProductType.containsStockType(it.type)
        }.map { it.productNumber }
    }

    private fun createStockMapBy(stockProductNumbers: List<String>): Map<String, Stock> {
        return stockRepository.findAllByProductNumberIn(stockProductNumbers).associateBy { it.productNumber }
    }

    private fun createCountingMapBy(stockProductNumbers: List<String>): Map<String, Int> {
        return stockProductNumbers.groupingBy { it }.eachCount()
    }
}
