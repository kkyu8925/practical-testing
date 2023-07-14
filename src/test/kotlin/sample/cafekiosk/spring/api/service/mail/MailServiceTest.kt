package sample.cafekiosk.spring.api.service.mail

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkClass
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import sample.cafekiosk.spring.client.mail.MailSendClient
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository

@ExtendWith(MockKExtension::class)
internal class MailServiceTest {

    @MockK(relaxed = true)
    private lateinit var mailSendClient: MailSendClient

    @MockK
    private lateinit var mailSendHistoryRepository: MailSendHistoryRepository

    @InjectMockKs
    private lateinit var mailService: MailService

    @DisplayName("메일 전송 테스트")
    @Test
    fun sendMail() {
        // given
        every {
            mailSendClient.sendEmail(allAny(), allAny(), allAny(), allAny())
        } returns true

        every {
            mailSendHistoryRepository.save(allAny())
        } returns mockkClass(MailSendHistory::class)

        // when
        val result = mailService.sendMail("", "", "", "")

        // then
        assertThat(result).isTrue()
        verify(exactly = 1) { mailSendHistoryRepository.save(allAny()) }
    }
}
