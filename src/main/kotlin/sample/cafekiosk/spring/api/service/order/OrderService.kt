package sample.cafekiosk.spring.api.service.order

import org.springframework.stereotype.Service
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.api.service.order.response.OrderResponse
import sample.cafekiosk.spring.domain.order.Order
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.product.ProductRepository
import java.time.LocalDateTime

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
) {

    fun createOrder(request: OrderCreateRequest, registeredDateTime: LocalDateTime): OrderResponse {
        val productNumbers = request.productNumbers
        val products = productRepository.findAllByProductNumberIn(productNumbers)

        val order = Order(products = products, registeredDateTime = registeredDateTime)
        val savedOrder = orderRepository.save(order)
        return OrderResponse.of(savedOrder)
    }
}
