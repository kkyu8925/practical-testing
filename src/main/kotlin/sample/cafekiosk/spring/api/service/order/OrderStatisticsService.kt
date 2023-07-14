package sample.cafekiosk.spring.api.service.order

import org.springframework.stereotype.Service
import sample.cafekiosk.spring.api.service.mail.MailService
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.order.OrderStatus
import java.time.LocalDate

@Service
class OrderStatisticsService(
    private val orderRepository: OrderRepository,
    private val mailService: MailService,
) {
    fun sendOrderStatisticsMail(orderDate: LocalDate, email: String): Boolean {
        val orders = orderRepository.findOrdersBy(
            orderDate.atStartOfDay(),
            orderDate.plusDays(1).atStartOfDay(),
            OrderStatus.PAYMENT_COMPLETED
        )

        val totalAmount = orders.sumOf { it.totalPrice }

        val result = mailService.sendMail(
            fromEmail = "no-reply@cafekiosk.com",
            toEmail = email,
            subject = String.format("[매출통계] %s", orderDate),
            content = String.format("총 매출 합계는 %s원입니다.", totalAmount)
        )

        require(result) { "매출 통계 메일 전송에 실패했습니다." }

        return true
    }
}
