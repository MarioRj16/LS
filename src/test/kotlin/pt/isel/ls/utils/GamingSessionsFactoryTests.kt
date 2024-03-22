package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.factories.GamingSessionFactory
import kotlin.test.assertEquals

class GamingSessionsFactoryTests {

    @Test
    fun `createRandomGamingSession() creates gaming session successfully`() {
        val db = DataMem()
        val game = db.games.create("game", "developer", setOf(Genre("FPS")))
        val gamingSessionFactory = GamingSessionFactory(db.gamingSessions)
        val session = gamingSessionFactory.createRandomGamingSession(game.id)

        assertEquals(session, db.gamingSessions.get(session.id))
    }
}