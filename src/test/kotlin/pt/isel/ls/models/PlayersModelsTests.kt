package pt.isel.ls.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString

class PlayersModelsTests {

    @Test
    fun `PlayerCreate should create a PlayerCreate object successfully`() {
        val name = generateRandomString()
        val email = generateRandomEmail()

        val playerCreate = PlayerCreate(name, email)

        assertEquals(name, playerCreate.name)
        assertEquals(email, playerCreate.email)
    }

    @Test
    fun `PlayerCreate should throw exception when email is not valid`() {
        val name = generateRandomString()
        val email = "john.doe.com"

        val exception = assertThrows<IllegalArgumentException> {
            PlayerCreate(name, email)
        }

        assertEquals("The given email is not in the right format", exception.message)
    }

    @Test
    fun `PlayerCreate should throw exception when name is blank`() {
        val name = ""
        val email = generateRandomEmail()

        val exception = assertThrows<IllegalArgumentException> {
            PlayerCreate(name, email)
        }

        assertEquals("Name must not be blank", exception.message)
    }
}