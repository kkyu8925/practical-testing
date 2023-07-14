package sample.cafekiosk.spring.api.service.mail

import org.springframework.stereotype.Service
import sample.cafekiosk.spring.client.mail.MailSendClient
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository

@Service
class MailService(
    private val mailSendClient: MailSendClient,
    private val mailSendHistoryRepository: MailSendHistoryRepository
) {

    fun sendMail(fromEmail: String, toEmail: String, subject: String, content: String): Boolean {
        val result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content)
        if (result) {
            mailSendHistoryRepository.save(
                MailSendHistory(
                    fromEmail = fromEmail,
                    toEmail = toEmail,
                    subject = subject,
                    content = content
                )
            )

            mailSendClient.a();
            mailSendClient.b();
            mailSendClient.c();

            return true
        }
        return false
    }
}
