package sample.cafekiosk.spring.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where selling_status in ('SELLING', 'HOLD');
     */
    fun findAllBySellingStatusIn(sellingStatuses: List<ProductSellingStatus>): List<Product>

    fun findAllByProductNumberIn(productNumbers: List<String>): List<Product>

    @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
    fun findLatestProductNumber(): String?
}
