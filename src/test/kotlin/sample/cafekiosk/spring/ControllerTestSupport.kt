package sample.cafekiosk.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import sample.cafekiosk.spring.api.controller.order.OrderController
import sample.cafekiosk.spring.api.controller.product.ProductController
import sample.cafekiosk.spring.api.service.order.OrderService
import sample.cafekiosk.spring.api.service.product.ProductService

@WebMvcTest(controllers = [OrderController::class, ProductController::class])
abstract class ControllerTestSupport {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @MockkBean(relaxed = true)
    protected lateinit var orderService: OrderService

    @MockkBean(relaxed = true)
    protected lateinit var productService: ProductService
}
