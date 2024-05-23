package pt.isel.ls.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomPassword
import pt.isel.ls.utils.generateRandomString

class PlayersModelsTests {

    @Test
    fun `PlayerCreate should create a PlayerCreate object successfully`() {
        val name = generateRandomString()
        val email = generateRandomEmail()
        val password = generateRandomPassword()
        val playerCreate = PlayerCreate(name, email, password)

        assertEquals(name, playerCreate.name)
        assertEquals(email, playerCreate.email)
    }
}
