package sample.cafekiosk.spring.domain.stock

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository : JpaRepository<Stock, Long> {

    fun findAllByProductNumberIn(productNumbers: List<String>): List<Stock>
}
