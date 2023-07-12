package sample.cafekiosk.spring.api.controller.order.request

import jakarta.validation.constraints.NotEmpty
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest

data class OrderCreateRequest(

    @field:NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    val productNumbers: List<String>
) {
    fun toServiceRequest(): OrderCreateServiceRequest {
        return OrderCreateServiceRequest(productNumbers)
    }
}
