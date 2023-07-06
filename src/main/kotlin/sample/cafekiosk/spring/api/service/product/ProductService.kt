package sample.cafekiosk.spring.api.service.product

import org.springframework.stereotype.Service
import sample.cafekiosk.spring.api.service.product.response.ProductResponse
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun getSellingProducts(): List<ProductResponse> {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay()).map {
            ProductResponse(
                id = it.id,
                productNumber = it.productNumber,
                type = it.type,
                sellingStatus = it.sellingStatus,
                name = it.name,
                price = it.price
            )
        }
    }
}
