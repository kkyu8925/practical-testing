package sample.cafekiosk.spring.api.controller.order

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.api.service.order.OrderService
import sample.cafekiosk.spring.api.service.order.response.OrderResponse
import java.time.LocalDateTime

@WebMvcTest(OrderController::class)
internal class OrderControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @MockkBean
    private lateinit var orderService: OrderService

    @DisplayName("신규 주문을 등록한다.")
    @Test
    fun createOrder() {
        // given
        val request = OrderCreateRequest(
            productNumbers = listOf("001")
        )
        every {
            orderService.createOrder(request.toServiceRequest(), allAny())
        } returns OrderResponse(
            id = 1,
            totalPrice = 1000,
            registeredDateTime = LocalDateTime.now(),
            products = listOf()
        )

        // when // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/orders/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
    }

    @DisplayName("신규 주문을 등록할 때 상품번호는 1개 이상이어야 한다.")
    @Test
    fun createOrderWithEmptyProductNumbers() {
        // given
        val request = OrderCreateRequest(
            productNumbers = listOf()
        )

        // when // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/orders/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("data"))
    }
}
