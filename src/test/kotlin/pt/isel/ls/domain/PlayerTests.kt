package pt.isel.ls.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomPassword
import pt.isel.ls.utils.generateRandomString
import java.util.*

class PlayerTests {
    private val validId = 1
    private val validName = generateRandomString()
    private val validEmail = generateRandomEmail()
    private val validPassword = generateRandomPassword().hash()
    private val token = UUID.randomUUID()

    @Test
    fun `Player is created successfully`() {
        assertDoesNotThrow {
            Player(validId, validName, validEmail, validPassword, token)
        }
    }

    @Test
    fun `Player throws exception for invalid ID`() {
        assertThrows<IllegalArgumentException> {
            Player(-1, validName, validEmail, validPassword, token)
        }
        assertThrows<IllegalArgumentException> {
            Player(0, validName, validEmail, validPassword, token)
        }
    }

    @Test
    fun `Player throws exception with blank name`() {
        assertThrows<IllegalArgumentException> {
            Player(validId, "", validEmail, validPassword, token)
        }
    }
}
