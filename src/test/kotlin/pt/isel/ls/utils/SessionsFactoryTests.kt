package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.factories.GamingSessionFactory
import kotlin.test.assertEquals

class SessionsFactoryTests {
    @Test
    fun `createRandomGamingSession() creates gaming session successfully`() {
        val db = DataMem()
        val name = generateRandomString()
        val developer = generateRandomString()
        val genres = setOf(Genre(1, "FPS"))

        val game = db.games.create(name, developer, genres)
        val playerCreate = PlayerCreate(generateRandomString(), generateRandomEmail())
        val player = db.players.create(playerCreate)
        val gamingSessionFactory = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        assertEquals(session, db.gamingSessions.get(session.id))
    }
}
