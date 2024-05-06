package pt.isel.ls.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import pt.isel.ls.utils.toLong

class SessionsModelsTests {

    @Test
    fun `SessionCreate should create a SessionCreate object successfully`() {
        val gameId = 1
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime()

        val sessionCreate = SessionCreate(gameId, capacity, startingDate.toLong())

        assertEquals(gameId, sessionCreate.gameId)
        assertEquals(startingDate.toLong(), sessionCreate.startingDate)
    }

    @Test
    fun `SessionUpdate should create a SessionUpdate object successfully`() {
        val capacity = 2
        val startingDate = plusDaysToCurrentDateTime()

        val sessionUpdate = SessionUpdate(capacity, startingDate.toLong())

        assertEquals(capacity, sessionUpdate.capacity)
        assertEquals(startingDate.toLong(), sessionUpdate.startingDate)
    }
}
