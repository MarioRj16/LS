package pt.isel.ls.utils.factories

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.SESSION_MAX_CAPACITY
import pt.isel.ls.SESSION_MIN_CAPACITY
import pt.isel.ls.data.GamesData
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.data.GenresData
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.isPast
import pt.isel.ls.utils.plusDaysToCurrentDateTime
import kotlin.random.Random

class GamingSessionFactory(
    private val gamingSessions: GamingSessionsData,
    gamesDB: GamesData,
    genresDB: GenresData,
    playersDB: PlayersData
) {
    private val gameFactory = GameFactory(gamesDB, genresDB)
    private val playerFactory = PlayerFactory(playersDB)

    fun createRandomGamingSession(
        gameId: Int? = null,
        hostId: Int? = null,
        players: Set<Player>? = null,
        isOpen: Boolean = true,
        date: LocalDateTime? = null
    ): Session {
        if(players != null && players.size > SESSION_MAX_CAPACITY)
            throw IllegalArgumentException("The number of players in a session must not exceed $SESSION_MAX_CAPACITY")

        if (date != null && date.isPast())
            throw IllegalArgumentException("The date must be in the future")


        if(players != null && players.size == SESSION_MAX_CAPACITY && isOpen)
            throw IllegalStateException("Session cannot be open and at max capacity at the same time")


        val sessionCapacity = when{
            players.isNullOrEmpty() && isOpen -> Random.nextInt(SESSION_MIN_CAPACITY, SESSION_MAX_CAPACITY)
            players.isNullOrEmpty() -> Random.nextInt(SESSION_MIN_CAPACITY, SESSION_MAX_CAPACITY+1)
            else -> Random.nextInt(maxOf(players.size, SESSION_MIN_CAPACITY), SESSION_MAX_CAPACITY)
        }

        val sessionDate = date ?: plusDaysToCurrentDateTime(Random.nextInt(1, 366).toLong())
        val sessionGame = gameId ?: gameFactory.createRandomGame().id
        val sessionHost = hostId ?: playerFactory.createRandomPlayer().id
        val sessionPlayers = when{
            players != null -> players
            isOpen -> List(Random.nextInt(0, minOf(sessionCapacity, SESSION_MIN_CAPACITY))) { playerFactory.createRandomPlayer() }.toSet()
            else -> List(sessionCapacity) { playerFactory.createRandomPlayer() }.toSet()
        }
        val session = gamingSessions.create(sessionCapacity, sessionGame, sessionDate, sessionHost)
        for (player in sessionPlayers)
            gamingSessions.addPlayer(session.id, player.id)
        return gamingSessions.get(session.id)!!
    }
}
