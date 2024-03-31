package pt.isel.ls.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class GameTests {
    private val validId = 1
    private val validName = "testName"
    private val validDeveloper = "testDeveloper"
    private val validGenres = setOf(Genre(1, "FPS"), Genre(2, "Action"))

    @Test
    fun `game can be created successfully`() {
        assertDoesNotThrow {
            Game(validId, validName, validDeveloper, validGenres)
        }
    }

    @Test
    fun `game throws exception for non positive ID`() {
        assertThrows<IllegalArgumentException> {
            Game(-1, validName, validDeveloper, validGenres)
        }

        assertThrows<IllegalArgumentException> {
            Game(0, validName, validDeveloper, validGenres)
        }
    }

    @Test
    fun `game throws exception with blank name`() {
        assertThrows<IllegalArgumentException> {
            Game(validId, "", validDeveloper, validGenres)
        }
    }

    @Test
    fun `game throws exception with blank developer`() {
        assertThrows<IllegalArgumentException> {
            Game(validId, validName, "", validGenres)
        }
    }

    @Test
    fun `game throws exception with no genres`() {
        assertThrows<IllegalArgumentException> {
            Game(validId, validName, validDeveloper, emptySet())
        }
    }
}
