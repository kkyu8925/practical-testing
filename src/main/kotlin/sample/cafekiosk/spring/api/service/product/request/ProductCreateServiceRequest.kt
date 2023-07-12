package sample.cafekiosk.spring.api.service.product.request

import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType

data class ProductCreateServiceRequest(
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int,
) {
    fun toEntity(nextProductNumber: String): Product {
        return Product(
            productNumber = nextProductNumber,
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price
        )
    }
}

