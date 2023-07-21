package sample.cafekiosk.spring.docs.product

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import sample.cafekiosk.spring.api.controller.product.ProductController
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest
import sample.cafekiosk.spring.api.service.product.ProductService
import sample.cafekiosk.spring.api.service.product.response.ProductResponse
import sample.cafekiosk.spring.docs.RestDocsSupport
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType

class ProductControllerDocsTest : RestDocsSupport() {

    private val productService: ProductService = mockk<ProductService>()

    override fun initController(): Any {
        return ProductController(productService)
    }

    @DisplayName("신규 상품을 등록하는 API")
    @Test
    fun createProduct() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "아메리카노",
            price = 4000,
        )

        every {
            productService.createProduct(request.createProductCreateServiceRequest())
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
            .andDo(
                document(
                    "product-create",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("type").type(JsonFieldType.STRING).description("상품 타입"),
                        fieldWithPath("sellingStatus").type(JsonFieldType.STRING).optional().description("상품 판매상태"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("상품 ID"),
                        fieldWithPath("data.productNumber").type(JsonFieldType.STRING).description("상품 번호"),
                        fieldWithPath("data.type").type(JsonFieldType.STRING).description("상품 타입"),
                        fieldWithPath("data.sellingStatus").type(JsonFieldType.STRING).description("상품 판매상태"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("상품 가격")
                    )
                )
            )
    }
}
