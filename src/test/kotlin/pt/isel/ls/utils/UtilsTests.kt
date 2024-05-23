package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.utils.values.Email
import pt.isel.ls.utils.values.Password
import kotlin.test.assertEquals

class UtilsTests {
    @Test
    fun `Email() creates instance if email is valid`() {
        val emailValue = "email@email.com"
        val email = Email(emailValue)
        assertEquals(emailValue, email.value)
    }

    @Test
    fun `Email() throws exception if email is invalid`() {
        assertThrows<IllegalArgumentException> {
            Email("invalidEmail")
        }
    }

    @Test
    fun `Password creates instance if password is valid`() {
        val passwordValue = "Strong@Password@123"
        val password = Password(passwordValue)
        assertEquals(passwordValue, password.value)
    }

    @Test
    fun `Password throws exception if password is invalid`() {
        assertThrows<IllegalArgumentException> {
            Password("pass")
        }
    }
}
