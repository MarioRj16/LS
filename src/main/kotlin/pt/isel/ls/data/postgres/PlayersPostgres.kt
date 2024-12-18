package pt.isel.ls.data.postgres

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerListElement
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.utils.postgres.toPlayer
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.utils.values.Email
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class PlayersPostgres(private val conn: () -> Connection) : PlayersData {
    override fun create(
        playerCreate: PlayerCreate,
    ): Player =
        conn().useWithRollback {
            val (name, email, password) = playerCreate
            val token = UUID.randomUUID()
            val hash = password.hash()
            val statement =
                it.prepareStatement(
                    """INSERT INTO PLAYERS(player_name, email, token, password) VALUES (?, ?, ?, ?)""",
                    Statement.RETURN_GENERATED_KEYS,
                ).apply {
                    setString(1, name)
                    setString(2, email.value)
                    setObject(3, token)
                    setString(4, hash)
                }

            if (statement.executeUpdate() == 0) {
                throw SQLException("Creating user failed, no rows affected")
            }

            val generatedKeys = statement.generatedKeys

            if (generatedKeys.next()) {
                return Player(generatedKeys.getInt(1), name, email, hash, token)
            }

            throw SQLException("Creating user failed, no ID was created")
        }

    override fun get(id: Int): Player? =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """select * from players where player_id = ?""",
                ).apply {
                    setInt(1, id)
                }

            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.toPlayer()
            }
            return null
        }

    override fun get(token: UUID): Player? =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """select * from players where token = ?""".trimIndent(),
                ).apply {
                    setObject(1, token)
                }

            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.toPlayer()
            }
            return null
        }

    override fun get(email: Email): Player? =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """select * from players where email = ?""",
                ).apply {
                    setString(1, email.value)
                }

            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.toPlayer()
            }
            return null
        }

    override fun get(username: String): Player? =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """select * from players where player_name = ?""",
                ).apply {
                    setString(1, username)
                }

            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.toPlayer()
            }
            return null
        }

    override fun search(searchParameters: PlayerSearch, skip: Int, limit: Int): PaginatedResponse<PlayerListElement> =
        conn().useWithRollback {
            val username = searchParameters.username
            val statement =
                it.prepareStatement(
                    """select * from players ${if (username.isNullOrBlank()) "" else "where player_name like ?"}""",
                ).apply {
                    username?.let {
                        setString(1, "$username%")
                    }
                }

            val resultSet = statement.executeQuery()
            val players = mutableListOf<PlayerListElement>()

            while (resultSet.next()) {
                players.add(PlayerListElement(resultSet.toPlayer()))
            }

            return PaginatedResponse.fromList(players, skip, limit)
        }
}
