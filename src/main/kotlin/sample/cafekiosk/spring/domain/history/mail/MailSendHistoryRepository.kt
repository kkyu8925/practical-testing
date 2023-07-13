package sample.cafekiosk.spring.domain.history.mail

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MailSendHistoryRepository : JpaRepository<MailSendHistory, Long>
