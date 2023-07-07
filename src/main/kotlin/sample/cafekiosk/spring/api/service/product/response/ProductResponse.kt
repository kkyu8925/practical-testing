package sample.cafekiosk.spring.api.service.product.response

import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType

data class ProductResponse(
    val id: Long,
    val productNumber: String,
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int
) {
    companion object {

        fun of(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id,
                productNumber = product.productNumber,
                type = product.type,
                sellingStatus = product.sellingStatus,
                name = product.name,
                price = product.price
            )
        }
    }
}
