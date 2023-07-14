package sample.cafekiosk.spring.client.mail

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.stereotype.Component

@Component
class MailSendClient {

    fun sendEmail(fromEmail: String, toEmail: String, subject: String, content: String): Boolean {
        log.info("메일 전송")
        throw IllegalArgumentException("메일 전송")
    }

    fun a() {
        log.info("a")
    }

    fun b() {
        log.info("b")
    }

    fun c() {
        log.info("c")
    }
}
