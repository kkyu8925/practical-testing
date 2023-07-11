package sample.cafekiosk.spring.api.controller.product

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sample.cafekiosk.spring.api.ApiResponse
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest
import sample.cafekiosk.spring.api.service.product.ProductService
import sample.cafekiosk.spring.api.service.product.response.ProductResponse

@RestController
class ProductController(
    private val productService: ProductService
) {
    @PostMapping("/api/v1/products/new")
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ApiResponse<ProductResponse> {
        return ApiResponse.ok(productService.createProduct(request))
    }

    @GetMapping("/api/v1/products/selling")
    fun getSellingProducts(): ApiResponse<List<ProductResponse>> {
        return ApiResponse.ok(productService.getSellingProducts())
    }
}
