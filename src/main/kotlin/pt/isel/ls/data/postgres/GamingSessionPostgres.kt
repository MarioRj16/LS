package pt.isel.ls.data.postgres

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.paginate
import pt.isel.ls.utils.postgres.toGamingSession
import pt.isel.ls.utils.postgres.toPlayer
import pt.isel.ls.utils.postgres.toPreviousGamingSession
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.utils.toTimeStamp
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class GamingSessionPostgres(private val conn: () -> Connection) : GamingSessionsData {
    override fun create(
        capacity: Int,
        game: Int,
        date: LocalDateTime,
    ): GamingSession =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """insert into gaming_sessions(capacity, starting_date, game) values (?, ?, ?)""",
                    Statement.RETURN_GENERATED_KEYS,
                ).apply {
                    setInt(1, capacity)
                    setTimestamp(2, date.toTimeStamp())
                    setInt(3, game)
                }

            if (statement.executeUpdate() == 0) {
                throw SQLException("Creating gaming session failed, no rows affected")
            }

            val generatedKeys = statement.generatedKeys

            if (generatedKeys.next()) {
                return GamingSession(generatedKeys.getInt(1), game, capacity, date, emptySet())
            }
            throw SQLException("Creating gaming session failed, no ID was created")
        }

    override fun get(sessionId: Int): GamingSession =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """
                    select * from gaming_sessions 
                    full outer join players_sessions 
                    on gaming_sessions.gaming_session_id = players_sessions.gaming_session
                    full outer join players
                    on players_sessions.player = players.player_id
                    where gaming_session_id = ?
                    """.trimIndent(),
                ).apply {
                    setInt(1, sessionId)
                }

            val resultSet = statement.executeQuery()
            val players = mutableSetOf<Player>()

            while (resultSet.next()) {
                try {
                    players += resultSet.toPlayer()
                } catch (e: NullPointerException) {
                }
                if (resultSet.isLast) {
                    return resultSet.toGamingSession(players)
                }
            }
            throw NoSuchElementException("Could not get gaming session, session with id $sessionId was not found")
        }

    override fun search(
        game: Int,
        date: LocalDateTime?,
        isOpen: Boolean?,
        player: Int?,
        limit: Int,
        skip: Int,
    ): List<GamingSession> =
        conn().useWithRollback {
            val query =
                """
                select * from gaming_sessions 
                left join players_sessions
                on gaming_sessions.gaming_session_id = players_sessions.gaming_session
                left join players
                on players_sessions.player = players.player_id
                where game = ?
                ${if (date != null) " and startingDate = ?" else ""}
                ${if (isOpen != null) " and startingDate <= CURRENT_TIMESTAMP" else ""}
                ${if (player != null) " and player = ?" else ""}
                order by gaming_sessions.gaming_session_id
                """.trimIndent()

            val statement =
                it.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                ).apply {
                    var parameterIndex = 1
                    setInt(parameterIndex++, game)
                    date?.let {
                        setTimestamp(parameterIndex++, date.toTimeStamp())
                    }
                    player?.let {
                        setInt(parameterIndex++, player)
                    }
                }

            val resultSet = statement.executeQuery()

            val sessions = mutableListOf<GamingSession>()
            val players = mutableSetOf<Player>()

            var previousSessionId: Int? = null
            while (resultSet.next()) {
                val currentSessionId = resultSet.getInt("gaming_session_id")
                if (previousSessionId != currentSessionId && previousSessionId != null) {
                    sessions += resultSet.toPreviousGamingSession(players.toSet(), previousSessionId)
                    players.clear()
                }
                if (resultSet.getInt("player_id") != 0)players += resultSet.toPlayer()
                previousSessionId = currentSessionId
            }
            if (resultSet.previous() && previousSessionId != null) sessions += resultSet.toPreviousGamingSession(players.toSet(), previousSessionId)

            /**
             *        while (resultSet.next()) {
             *                 val currentGameId = resultSet.getInt("game_id")
             *
             *                 if ( currentGameId!=previousGameId && previousGameId!=null) {
             *                     games += resultSet.toPreviousGame(foundGenres.toSet(),previousGameId)
             *                     foundGenres.clear()
             *                 }
             *                 foundGenres += resultSet.toGenre()
             *                 previousGameId=currentGameId
             *             }
             *
             *             if (resultSet.previous()&&previousGameId!=null)games += resultSet.toPreviousGame(foundGenres.toSet(),previousGameId)
             *
             */
            return sessions.paginate(skip, limit)
        }

    override fun addPlayer(
        session: Int,
        player: Int,
    ) = conn().useWithRollback {
        // TODO Throw ConflictException if the same player tries enter twice in the same session
        val stm =
            it.prepareStatement(
                """insert into players_sessions(player, gaming_session) values (?, ?)""",
                Statement.RETURN_GENERATED_KEYS,
            ).apply {
                setInt(1, player)
                setInt(2, session)
            }

        if (stm.executeUpdate() == 0 || !stm.generatedKeys.next()) {
            throw SQLException("Adding player to session failed, no rows affected")
        }
    }
}
