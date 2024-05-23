package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.factories.GamingSessionFactory
import kotlin.test.assertEquals

class SessionsFactoryTests {
    @Test
    fun `createRandomGamingSession() creates gaming session successfully`() {
        val db = DataMem()
        val genres = setOf(Genre(1, "FPS"))

        val gameCreate = GameCreate.create(genres = genres.map { it.genreId }.toSet())
        val game = db.games.create(gameCreate, genres)
        val playerCreate = PlayerCreate.create()
        val player = db.players.create(playerCreate)
        val gamingSessionFactory = GamingSessionFactory(db.gamingSessions, db.games, db.genres, db.players)
        val session = gamingSessionFactory.createRandomGamingSession(game.id, player.id)

        assertEquals(session, db.gamingSessions.get(session.id))
    }
}
