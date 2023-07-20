package sample.cafekiosk.spring.api.service.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest
import sample.cafekiosk.spring.api.service.product.response.ProductResponse
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus

@Transactional(readOnly = true)
@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productNumberFactory: ProductNumberFactory,
) {
    @Transactional
    fun createProduct(request: ProductCreateServiceRequest): ProductResponse {
        val nextProductNumber = productNumberFactory.createNextProductNumber()

        val product = request.toEntity(nextProductNumber)
        val savedProduct = productRepository.save(product)

        return ProductResponse.of(savedProduct)
    }

    fun getSellingProducts(): List<ProductResponse> {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay()).map {
            ProductResponse.of(it)
        }
    }
}
