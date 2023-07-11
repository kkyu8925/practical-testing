package sample.cafekiosk.spring.api.controller.order.request

import jakarta.validation.constraints.NotEmpty

data class OrderCreateRequest(

    @field:NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    val productNumbers: List<String>
)
