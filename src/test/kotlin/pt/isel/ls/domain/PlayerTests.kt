package pt.isel.ls.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*

class PlayerTests {
    private val validId = 1
    private val validName = "testName"
    private val validEmail = "testEmail@email.com"
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
    fun `Player throws exception with email in invalid format`() {
        assertThrows<IllegalArgumentException> {
            Player(validId, validName, "email@email.", token)
        }
        assertThrows<IllegalArgumentException> {
            Player(validId, validName, "@mail.com", token)
        }
        assertThrows<IllegalArgumentException> {
            Player(validId, validName, "", token)
        }
    }

    @Test
    fun `Player throws exception with blank name`() {
        assertThrows<IllegalArgumentException> {
            Player(validId, "", validEmail, token)
        }
    }
}
