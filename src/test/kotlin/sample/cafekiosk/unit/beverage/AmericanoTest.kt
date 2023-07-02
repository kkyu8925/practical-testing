package sample.cafekiosk.unit.beverage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AmericanoTest {

    @Test
    fun getName() {
        val americano = Americano()

        assertEquals(americano.getName(), "아메리카노");
        assertThat(americano.getName()).isEqualTo("아메리카노")
    }

    @Test
    fun getPrice() {
        val americano = Americano()

        assertThat(americano.getPrice()).isEqualTo(4000)
    }
}
