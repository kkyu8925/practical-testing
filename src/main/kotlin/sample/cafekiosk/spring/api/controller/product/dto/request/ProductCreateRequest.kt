package sample.cafekiosk.spring.api.controller.product.dto.request

import jakarta.validation.constraints.Positive
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType

data class ProductCreateRequest(

    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,

    @field:Positive(message = "상품 가격은 양수여야 합니다.")
    val price: Int,
) {
    fun createProductCreateServiceRequest(): ProductCreateServiceRequest {
        return ProductCreateServiceRequest(
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price
        )
    }
}
