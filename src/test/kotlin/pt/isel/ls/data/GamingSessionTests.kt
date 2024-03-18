package pt.isel.ls.data

import kotlinx.datetime.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.utils.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GamingSessionTests: DataMem() {

    val futureDate = LocalDateTime(
        LocalDate(2050, 3, 3),
        LocalTime(1, 1, 1, 1)
    )

    val pastDate = LocalDateTime(
        LocalDate(2020, 3, 3),
        LocalTime(1, 1, 1, 1)
    )

    val default_skip = 0
    val default_limit = 30

    private companion object: AppFactory(DataMem())

    @BeforeEach
    fun setUp() = reset()

    @Test
    fun`create() returns gaming session successfully`(){
        val game = gameFactory.createRandomGame()
        val capacity = 1
        val date = LocalDateTime(
            LocalDate(2050, 3, 3),
            LocalTime(1, 1, 1, 1)
        )
        val session = gamingSessions.create(capacity, game.id, date)

        assertTrue(session.id == 1)
        assertEquals(game.id, session.game)
        assertEquals(capacity, session.capacity)
        assertEquals(date, session.startingDate)
        assertTrue(session.players.isEmpty())
        assertTrue(session.state)
    }

    @Test
    fun `create() throws exception for non existing game`(){
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(1, 1, futureDate)
        }
    }

    @Test
    fun `create() throws exception for invalid capacity`(){
        val game = gameFactory.createRandomGame()
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(-1, game.id, futureDate)
        }
    }

    @Test
    fun `create() throws exception for past starting date`(){
        val game = gameFactory.createRandomGame()
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(1, game.id, pastDate)
        }
    }

    @Test
    fun `get() returns gaming session successfully`(){
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)

        assertEquals(session, gamingSessions.get(session.id))
    }

    @Test
    fun `get() throws exception for non existing gaming session`(){
        assertThrows<NoSuchElementException> {
            gamingSessions.get(1)
        }
    }

    @Test
    fun `addPlayer() adds player to gaming session successfully`(){
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)
        val player = playerFactory.createRandomPlayer()

        gamingSessions.addPlayer(session.id, player.id)

        val playersInSession = gamingSessions.get(session.id).players

        assertTrue(player in playersInSession)
    }

    @Test
    fun `addPlayer() throws exception for non existing gaming session`(){
        assertThrows<IllegalArgumentException> {
            val player = playerFactory.createRandomPlayer()
            gamingSessions.addPlayer(1, player.id)
        }
    }

    @Test
    fun `addPlayer() throws exception if gaming session is already at max capacity`(){
        val game = gameFactory.createRandomGame()
        val session = gamingSessionFactory.createRandomGamingSession(game.id)

        for(i in 1 until session.capacity){
            val player = playerFactory.createRandomPlayer()
            gamingSessions.addPlayer(session.id, player.id)
        }

        assertThrows<IllegalArgumentException> {
            val player = playerFactory.createRandomPlayer()
            gamingSessions.addPlayer(session.id, player.id)
        }
    }

    @Test
    fun `addPlayer() throws exception for session past starting date`(){
        val game = gameFactory.createRandomGame()
        val player = playerFactory.createRandomPlayer()
        val nanosToAdd = 100L
        val date = LocalDateTime(
            java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalTime.now().plusNanos(nanosToAdd).toKotlinLocalTime()
        )
        val session = gamingSessions.create(1, game.id, date)
        assertThrows<IllegalArgumentException> {
            Thread.sleep(nanosToAdd)
            gamingSessions.addPlayer(session.id, player.id)
        }
    }

    @Test
    fun `search() returns gaming sessions successfully`(){
        val game = gameFactory.createRandomGame()
        val game2 = gameFactory.createRandomGame()

        assertTrue(
            gamingSessions.search(game.id, null, null, null, default_limit, default_skip).isEmpty()
        )

        val session = gamingSessionFactory.createRandomGamingSession(game.id)
        val session2 = gamingSessionFactory.createRandomGamingSession(game.id)
        val session3 = gamingSessionFactory.createRandomGamingSession(game2.id)

        var searchResults =
            gamingSessions.search(game.id, null, null, null, default_limit, default_skip)
        assertTrue(searchResults.size == 2)
        assertContains(searchResults, session)
        assertContains(searchResults, session2)

        searchResults =
            gamingSessions.search(game2.id, null, null, null, 1, 0)

        assertTrue(searchResults.size == 1)
        assertContains(searchResults, session3)
    }

    @Test
    fun`search() throws exception for non existing game`(){
        val game = gameFactory.createRandomGame()
        gamingSessionFactory.createRandomGamingSession(game.id)
        assertThrows<IllegalArgumentException> {
            gamingSessions.search(10, null, null, null, default_limit, default_skip)
        }
    }
}