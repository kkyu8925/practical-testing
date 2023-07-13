package sample.cafekiosk.spring.domain.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        "select o from Order o where o.registeredDateTime >= :startDateTime" +
                " and o.registeredDateTime < :endDateTime" +
                " and o.orderStatus = :orderStatus"
    )
    fun findOrdersBy(startDateTime: LocalDateTime, endDateTime: LocalDateTime, orderStatus: OrderStatus): List<Order>
}
