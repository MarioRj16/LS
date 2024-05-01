package pt.isel.ls.domain

import java.util.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.utils.Email

class PlayerTests {
    private val validId = 1
    private val validName = "testName"
    private val validEmail = Email("testEmail@email.com")
    private val token = UUID.randomUUID()

    @Test
    fun `Player is created successfully`() {
        assertDoesNotThrow {
            Player(validId, validName, validEmail, token)
        }
    }

    @Test
    fun `Player throws exception for invalid ID`() {
        assertThrows<IllegalArgumentException> {
            Player(-1, validName, validEmail, token)
        }
        assertThrows<IllegalArgumentException> {
            Player(0, validName, validEmail, token)
        }
    }

    @Test
    fun `Player throws exception with blank name`() {
        assertThrows<IllegalArgumentException> {
            Player(validId, "", validEmail, token)
        }
    }
}
