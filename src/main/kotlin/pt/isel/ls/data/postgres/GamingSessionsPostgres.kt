package pt.isel.ls.data.postgres

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import kotlinx.datetime.LocalDateTime
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.paginate
import pt.isel.ls.utils.postgres.toGamingSession
import pt.isel.ls.utils.postgres.toPlayer
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.utils.toTimeStamp

class GamingSessionsPostgres(private val conn: () -> Connection) : GamingSessionsData {
    override fun create(
        capacity: Int,
        game: Int,
        date: LocalDateTime,
        hostId: Int,
    ): Session =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """insert into gaming_sessions(capacity, starting_date, game, creator) values (?, ?, ?, ?)""",
                    Statement.RETURN_GENERATED_KEYS,
                ).apply {
                    setInt(1, capacity)
                    setTimestamp(2, date.toTimeStamp())
                    setInt(3, game)
                    setInt(4, hostId)
                }

            if (statement.executeUpdate() == 0) {
                throw SQLException("Creating gaming session failed, no rows affected")
            }

            val generatedKeys = statement.generatedKeys

            if (generatedKeys.next()) {
                return Session(generatedKeys.getInt(1), game, hostId, capacity, date, emptySet())
            }
            throw SQLException("Creating gaming session failed, no ID was created")
        }

    override fun get(sessionId: Int): Session? =
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
                if (resultSet.getString("player_id") != null) {
                    players += resultSet.toPlayer()
                }
                if (resultSet.isLast) {
                    return resultSet.toGamingSession(players)
                }
            }
            return null
        }

    override fun search(sessionParameters: SessionSearch, limit: Int, skip: Int): List<Session> =
        conn().useWithRollback {
            val (game, date, isOpen, playerEmail, hostId) = sessionParameters
            val query =
                """
                select * from gaming_sessions
                full outer join 
                (select gaming_session, count(player) player_count from players_sessions group by gaming_session) as gspc 
                on gaming_session_id = gspc.gaming_session
                ${if (playerEmail != null) 
                    " full outer join players_sessions on gaming_session_id = gspc.gaming_session"
                else 
                    ""
                }
                ${if (playerEmail != null) " full outer join players on player = player_id" else ""}
                where 1 = 1
                ${if (game != null) " and game = ?" else ""}
                ${if (date != null) " and starting_date = ?" else ""}
                ${if (playerEmail != null) " and email = ?" else ""}
                ${if(hostId != null) " and creator = ?" else ""}
                ${
                    if (isOpen != null) {
                        if (isOpen) {
                            " and starting_date > CURRENT_TIMESTAMP and player_count < capacity"
                        } else {
                            " and starting_date <= CURRENT_TIMESTAMP or player_count > capacity"
                        }
                    } else {
                        ""
                    }
                }
                order by gaming_sessions.gaming_session_id
                """.trimIndent()

            val statement =
                it.prepareStatement(query).apply {
                    var parameterIndex = 1
                    game?.let { setInt(parameterIndex++, game) }
                    date?.let { setTimestamp(parameterIndex++, date.toTimeStamp()) }
                    playerEmail?.let { setString(parameterIndex++, playerEmail.email) }
                    hostId?.let { setInt(parameterIndex++, hostId) }
                }

            val resultSet = statement.executeQuery()

            val sessions = mutableListOf<Session>()

            while (resultSet.next()) {
                sessions += resultSet.toGamingSession(emptySet())
            }

            return sessions.distinct().paginate(skip, limit)
        }

    override fun update(sessionId: Int, sessionUpdate: SessionUpdate) =
        conn().useWithRollback {
            val (capacity, startingDate) = sessionUpdate

            val stm =
                it.prepareStatement(
                    """update gaming_sessions set capacity = ?, starting_date = ? where gaming_session_id = ?""",
                ).apply {
                    setInt(1, capacity)
                    setTimestamp(2, startingDate.toTimeStamp())
                    setInt(3, sessionId)
                }

            if (stm.executeUpdate() == 0) {
                throw SQLException("Updating gaming session failed, no rows affected")
            }
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
                return resultSet.toGamingSession(emptySet()).hostId == playerId
            }
            return false
        }
}
