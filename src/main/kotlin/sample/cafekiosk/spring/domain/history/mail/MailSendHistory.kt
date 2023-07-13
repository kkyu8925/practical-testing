package sample.cafekiosk.spring.domain.history.mail

import jakarta.persistence.Entity
import sample.cafekiosk.spring.domain.BaseEntity

@Entity
class MailSendHistory(
    val fromEmail: String,
    val toEmail: String,
    val subject: String,
    val content: String,
) : BaseEntity()
