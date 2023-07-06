package sample.cafekiosk.spring.api.service.product.response

import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType

data class ProductResponse (
    val id: Long,
    val productNumber: String,
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int
)
