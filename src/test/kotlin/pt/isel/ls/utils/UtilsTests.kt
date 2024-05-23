package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.utils.values.Email
import pt.isel.ls.utils.values.Password
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UtilsTests {
    @Test
    fun `paginate() does pagination correctly`() {
        val intList = List(5) { it }

        assertEquals(intList, intList.paginate(0, 5))
        assertTrue(intList.paginate(2, 0).isEmpty())
        assertTrue(intList.paginate(5, 0).isEmpty())
        assertEquals(intList.drop(2), intList.paginate(2, 3))
    }

    @Test
    fun `paginate() throws exception if limit or skip are not valid`() {
        val l = List(10) { it }
        assertThrows<IllegalArgumentException> {
            l.paginate(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            l.paginate(2, -1)
        }
        assertThrows<IllegalArgumentException> {
            l.paginate(-1, -2)
        }
    }

    @Test
    fun `paginate() returns empty list if skip is greater than list size`() {
        val l = List(10) { it }
        assertTrue(l.paginate(20, DEFAULT_LIMIT).isEmpty())
    }

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
