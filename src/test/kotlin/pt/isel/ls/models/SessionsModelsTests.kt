package pt.isel.ls.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.utils.minusDaysToCurrentDateTime
import pt.isel.ls.utils.plusDaysToCurrentDateTime

class SessionsModelsTests {

    @Test
    fun `SessionCreate should create a SessionCreate object successfully`() {
        val gameId = 1
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime()

        val sessionCreate = SessionCreate(gameId, capacity, startingDate)

        assertEquals(gameId, sessionCreate.gameId)
        assertEquals(startingDate, sessionCreate.startingDate)
    }

    @Test
    fun `SessionUpdate should create a SessionUpdate object successfully`() {
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime()

        val sessionUpdate = SessionUpdate(capacity, startingDate)

        assertEquals(capacity, sessionUpdate.capacity)
        assertEquals(startingDate, sessionUpdate.startingDate)
    }

    @Test
    fun `SessionUpdate should throw exception when capacity is less than 2`() {
        val capacity = 1
        val startingDate = plusDaysToCurrentDateTime()

        val exception = assertThrows<IllegalArgumentException> {
            SessionUpdate(capacity, startingDate)
        }

        assertEquals("Capacity must be greater than 1", exception.message)
    }

    @Test
    fun `SessionUpdate should throw exception when starting date is in the past`() {
        val capacity = 2
        val startingDate = minusDaysToCurrentDateTime(1)

        val exception = assertThrows<IllegalArgumentException> {
            SessionUpdate(capacity, startingDate)
        }

        assertEquals("Starting date must be in the future", exception.message)
    }
}
