package sample.cafekiosk.spring.api.controller.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest
import sample.cafekiosk.spring.api.service.product.ProductService
import sample.cafekiosk.spring.api.service.product.response.ProductResponse
import sample.cafekiosk.spring.domain.product.ProductSellingStatus.*
import sample.cafekiosk.spring.domain.product.ProductType.*

@WebMvcTest(ProductController::class)
internal class ProductControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var productService: ProductService

    @DisplayName("신규 상품을 등록한다.")
    @Test
    fun createProduct() {
        // given
        val request = ProductCreateRequest(
            type = HANDMADE,
            sellingStatus = SELLING,
            name = "아메리카노",
            price = 4000
        )
        every {
            productService.createProduct(request)
        } returns ProductResponse(
            id = 4,
            productNumber = "004",
            type = request.type,
            sellingStatus = request.sellingStatus,
            name = request.name,
            price = request.price
        )

        // when // then
        mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
    }

    @DisplayName("신규 상품을 등록할 때 상품 가격은 양수이다.")
    @Test
    fun createProductWithZeroPrice() {
        // given
        val request = ProductCreateRequest(
            type = HANDMADE,
            sellingStatus = SELLING,
            name = "아메리카노",
            price = 0
        )
        every {
            productService.createProduct(request)
        } returns mockkClass(ProductResponse::class)

        // when // then
        mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
            .andExpect(jsonPath("$.data").value("data"))
    }

    @Test
    @DisplayName("판매 상품을 조회한다.")
    fun sellingProducts() {
        // given
        every {
            productService.getSellingProducts()
        } returns listOf(
            ProductResponse(
                id = 4,
                productNumber = "004",
                type = HANDMADE,
                sellingStatus = SELLING,
                name = "아메리카노",
                price = 4000
            )
        )

        // when // then
        mockMvc.perform(
            get("/api/v1/products/selling")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isArray())
    }
}
