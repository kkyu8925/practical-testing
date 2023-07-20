package sample.cafekiosk.spring

import com.ninjasquad.springmockk.MockkBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.cafekiosk.spring.client.mail.MailSendClient

@ActiveProfiles("test")
@SpringBootTest
abstract class IntegrationTestSupport {

    @MockkBean(relaxed = true)
    protected lateinit var mailSendClient: MailSendClient
}
