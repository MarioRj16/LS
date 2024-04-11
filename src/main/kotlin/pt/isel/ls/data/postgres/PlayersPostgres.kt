package pt.isel.ls.data.postgres

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.postgres.toPlayer
import pt.isel.ls.utils.postgres.useWithRollback
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class PlayersPostgres(private val conn: () -> Connection) : PlayersData {
    override fun create(
        playerCreate: PlayerCreate,
    ): Player =
        conn().useWithRollback {
            val (name, email) = playerCreate
            val token = UUID.randomUUID()
            val statement =
                it.prepareStatement(
                    """INSERT INTO PLAYERS(player_name, email, token) VALUES (?, ?, ?)""".trimIndent(),
                    Statement.RETURN_GENERATED_KEYS,
                ).apply {
                    setString(1, name)
                    setString(2, email)
                    setObject(3, token)
                }

            if (statement.executeUpdate() == 0) {
                throw SQLException("Creating user failed, no rows affected")
            }

            val generatedKeys = statement.generatedKeys

            if (generatedKeys.next()) {
                return Player(generatedKeys.getInt(1), name, email, token)
            }

            throw SQLException("Creating user failed, no ID was created")
        }

    override fun get(id: Int): Player? =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """select * from players where player_id = ?""".trimIndent(),
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

    override fun get(email: String): Player? =
        conn().useWithRollback {
            val statement =
                it.prepareStatement(
                    """select * from players where email = ?""".trimIndent(),
                ).apply {
                    setString(1, email)
                }

            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.toPlayer()
            }
            return null
        }
}
