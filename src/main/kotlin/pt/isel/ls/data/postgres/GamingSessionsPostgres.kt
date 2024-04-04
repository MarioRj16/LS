package pt.isel.ls.data.postgres

import kotlinx.datetime.LocalDateTime
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.paginate
import pt.isel.ls.utils.postgres.toGamingSession
import pt.isel.ls.utils.postgres.toPlayer
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.utils.toTimeStamp
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class GamingSessionsPostgres(private val conn: () -> Connection) : GamingSessionsData {
    override fun create(
        capacity: Int,
        game: Int,
        date: LocalDateTime,
        playerId: Int,
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
                return GamingSession(generatedKeys.getInt(1), game, playerId, capacity, date, emptySet())
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
                players += resultSet.toPlayer()
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
                full outer join players_sessions
                on gaming_sessions.gaming_session_id = players_sessions.gaming_session
                full outer join players
                on players_sessions.player = players.player_id
                where game = $game
                ${if (date != null) " and startingDate = ?" else ""}
                ${if (isOpen != null) " and startingDate <= CURRENT_TIMESTAMP" else ""}
                ${if (player != null) " and player = ?" else ""}
                order by gaming_sessions.gaming_session_id
                """.trimIndent()

            val statement =
                it.prepareStatement(query).apply {
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
                players += resultSet.toPlayer()
                val currentSessionId = resultSet.getInt("gaming_session_id")
                if (previousSessionId == currentSessionId) {
                    sessions += resultSet.toGamingSession(players.toSet())
                    players.clear()
                    continue
                }
                previousSessionId = currentSessionId
            }
            return sessions.paginate(skip, limit)
        }

    override fun update(sessionId: Int, newDateTime: LocalDateTime, newCapacity: Int): GamingSession =
        conn().useWithRollback {
            val stm2 = it.prepareStatement(
                """
                select * from players where player_id in (select player from players_sessions where gaming_session = ?)
                """.trimIndent(),
            ).apply {
                setInt(1, sessionId)
            }

            val resultSet2 = stm2.executeQuery()

            val players = mutableSetOf<Player>()

            while (resultSet2.next()) {
                players += resultSet2.toPlayer()
            }

            val stm =
                it.prepareStatement(
                    """update gaming_sessions set capacity = ?, starting_date = ? where gaming_session_id = ? RETURNING *""",
                ).apply {
                    setInt(1, newCapacity)
                    setTimestamp(2, newDateTime.toTimeStamp())
                    setInt(3, sessionId)
                }

            val resultSet = stm.executeQuery()
            while (resultSet.next()) {
                return resultSet.toGamingSession(players)
            }
            throw SQLException("Updating gaming session failed, no rows affected")
        }

    override fun delete(sessionId: Int) =
        conn().useWithRollback {
            val stm =
                it.prepareStatement("""delete from gaming_sessions where gaming_session_id = ?""").apply {
                    setInt(1, sessionId)
                }

            if (stm.executeUpdate() == 0) {
                throw SQLException("Gaming session could not be deleted, no rows affected")
            }
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

    override fun removePlayer(
        sessionId: Int,
        playerId: Int,
    ) = conn().useWithRollback {
        val stm = it.prepareStatement(
            """delete from players_sessions where gaming_session = ? and player = ?""",
        ).apply {
            setInt(1, sessionId)
            setInt(2, playerId)
        }

        if (stm.executeUpdate() == 0) {
            throw SQLException("Player could not be removed from gaming session, no rows affected")
        }
    }

    override fun isOwner(
        sessionId: Int,
        playerId: Int,
    ): Boolean =
        conn().useWithRollback {
            val stm =
                it.prepareStatement("""select * from gaming_sessions where gaming_session_id = ?""")
                    .apply {
                        setInt(1, sessionId)
                        setInt(2, playerId)
                    }

            val resultSet = stm.executeQuery()

            if (resultSet.next()) {
                return resultSet.toGamingSession(emptySet()).creatorId == playerId
            }
            throw NoSuchElementException("No session $sessionId was found")
        }
}
