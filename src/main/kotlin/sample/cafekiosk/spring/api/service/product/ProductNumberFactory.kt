package sample.cafekiosk.spring.api.service.product

import org.springframework.stereotype.Component
import sample.cafekiosk.spring.domain.product.ProductRepository

@Component
class ProductNumberFactory(
    private val productRepository: ProductRepository
) {
    fun createNextProductNumber(): String {
        val latestProductNumber = productRepository.findLatestProductNumber() ?: return "001"

        val latestProductNumberInt = latestProductNumber.toInt()
        val nextProductNumberInt = latestProductNumberInt + 1

        return String.format("%03d", nextProductNumberInt)
    }
}

