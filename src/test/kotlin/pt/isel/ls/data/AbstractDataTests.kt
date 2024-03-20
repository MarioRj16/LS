package pt.isel.ls.data

import org.junit.jupiter.api.BeforeEach
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.factories.GameFactory
import pt.isel.ls.utils.factories.GamingSessionFactory
import pt.isel.ls.utils.factories.PlayerFactory

abstract class AbstractDataTests {
    private val db = DataMem()

    protected val players = db.players
    protected val games = db.games
    protected val gamingSessions = db.gamingSessions

    protected val playerFactory = PlayerFactory(db.players)
    protected val gameFactory = GameFactory(db.games)
    protected val gamingSessionFactory = GamingSessionFactory(db.gamingSessions)

    @BeforeEach
    fun setUp(){
        db.reset()
    }

    // TODO: Find a way of running these two tests only once, without running setUp()
    /*
    @Test
    fun `populate() populates db with dummy data`(){
        assertTrue(db.playersDB.table.isEmpty())
        assertTrue(db.gamesDB.table.isEmpty())
        assertTrue(db.gamingSessionsDB.table.isEmpty())

        db.populate()

        assertTrue(db.playersDB.table.isNotEmpty())
        assertTrue(db.gamesDB.table.isNotEmpty())
        assertTrue(db.gamingSessionsDB.table.isNotEmpty())
    }

    @Test
    fun `reset() clears whole db`(){
        db.populate()
        db.reset()
        assertTrue(db.playersDB.table.isEmpty())
        assertTrue(db.gamesDB.table.isEmpty())
        assertTrue(db.gamingSessionsDB.table.isEmpty())
    }
    */

}