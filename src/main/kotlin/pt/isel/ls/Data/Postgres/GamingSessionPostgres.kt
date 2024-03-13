package pt.isel.ls.Data.Postgres

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Domain.GamingSession
import pt.isel.ls.Domain.Player
import pt.isel.ls.utils.exceptions.ObjectDoesNotExistException
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class GamingSessionPostgres(private val conn: Connection): GamingSessionStorage {
    override fun create(capacity: Int, game: Int, date: LocalDateTime): GamingSession = conn.use {
        val session: GamingSession

        val stm = it.prepareStatement(
            """insert into gaming_sessions(capacity, starting_date, game) values (?, ?, ?)""",
            Statement.RETURN_GENERATED_KEYS
        )
        stm.setInt(1, capacity)
        stm.setDate(2, date)
        stm.setInt(3, game)

        if(stm.executeUpdate() == 0)
            throw SQLException("Creating gaming session failed, no rows affected")

        val generatedKeys = stm.generatedKeys

        if(generatedKeys.next())
            session = get(generatedKeys.getInt(1))!!
        else
            throw SQLException("Creating gaming session failed, no ID was created")

        return session
    }

    override fun get(sessionId: Int): GamingSession? = conn.use {
        val sessionStm = it.prepareStatement("""select * from gaming_sessions where gaming_session_id = ?""".trimIndent())

        sessionStm.setInt(1, sessionId)

        val sessionRS = sessionStm.executeQuery()

        if(sessionRS.next()){
            //val id = sessionRS.getInt(1)
            val capacity = sessionRS.getInt(2)
            val startingDate = sessionRS.getDate(3) // TODO: Find out how to get this as LocalDateTime
            val game = sessionRS.getInt(4)

            val players = mutableSetOf<Player>()
            val playerStm = it.prepareStatement("""select * from players_sessions where gaming_session = ?""")

            playerStm.setInt(1, sessionId)

            val playerRS = playerStm.executeQuery()

            while (playerRS.next()){
                players.add(
                    Player(
                        id = playerRS.getInt(1),
                        name = playerRS.getString(2),
                        email = playerRS.getString(3),
                        token = UUID.fromString(playerRS.getString(4))
                    )
                )
            }

            val session: GamingSession = GamingSession(
                id = sessionId,
                game = game,
                capacity = capacity,
                startingDate = startingDate,
                players = players.toSet()
            )
            return session
        }
        throw ObjectDoesNotExistException("Could not get gaming session, session with id $sessionId was not found")
    }

    override fun search(
        game: Int,
        date: LocalDateTime?,
        isOpen: Boolean?, // TODO: Write a condition for this
        player: Int?,
        limit: Int,
        skip: Int
    ): List<GamingSession> {
        TODO("Not yet implemented")
    }

    override fun addPlayer(session: Int, player: Int): Boolean = conn.use {
        val sessionStm = it.prepareStatement("""select * from gaming_sessions where gaming_session_id = ?""")

        sessionStm.setInt(1, session)
        val sessionRS = sessionStm.executeQuery()

        if(!sessionRS.next())
            throw ObjectDoesNotExistException("Could not get gaming session, session with id $session was not found")

        val playerStm = it.prepareStatement("""select * from players where player_id = ?""")

        playerStm.setInt(1, player)
        val playerRS = playerStm.executeQuery()

        if(!playerRS.next())
            throw ObjectDoesNotExistException("Could not get player, player with id $player was not found")

        val stm = it.prepareStatement(
            """insert into players_sessions(players, gaming_session) values (?, ?)""",
            Statement.RETURN_GENERATED_KEYS
        )

        stm.setInt(1, player)
        stm.setInt(2, session)

        if(stm.executeUpdate() == 0 || !stm.generatedKeys.next())
            throw SQLException("Adding player to session failed, no rows affected")

        return true // TODO: Change this function return to Unit
    }
}