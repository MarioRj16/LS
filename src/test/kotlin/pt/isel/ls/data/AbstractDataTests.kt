package pt.isel.ls.data

import org.junit.jupiter.api.BeforeEach
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.GenresFactory
import pt.isel.ls.utils.factories.PlayerFactory

abstract class AbstractDataTests {
    private val db = DataMem()

    protected val players = db.players
    protected val games = db.games
    protected val gamingSessions = db.gamingSessions
    protected val genres = db.genres

    protected val genreFactory = GenresFactory(genres)
    protected val playerFactory = PlayerFactory(players)
    protected val gameFactory = GameFactory(games, genres)
    protected val gamingSessionFactory = GamingSessionFactory(gamingSessions, games, genres, players)

    @BeforeEach
    fun setUp() {
        db.reset()
    }
}
