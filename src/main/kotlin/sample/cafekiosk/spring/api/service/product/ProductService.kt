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
    private val productRepository: ProductRepository
) {
    @Transactional
    fun createProduct(request: ProductCreateServiceRequest): ProductResponse {
        val nextProductNumber = createNextProductNumber()

        val product = request.toEntity(nextProductNumber)
        val savedProduct = productRepository.save(product)

        return ProductResponse.of(savedProduct)
    }

    fun getSellingProducts(): List<ProductResponse> {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay()).map {
            ProductResponse.of(it)
        }
    }

    private fun createNextProductNumber(): String {
        val latestProductNumber = productRepository.findLatestProductNumber() ?: return "001"

        val latestProductNumberInt = latestProductNumber.toInt()
        val nextProductNumberInt = latestProductNumberInt + 1

        return String.format("%03d", nextProductNumberInt)
    }
}
