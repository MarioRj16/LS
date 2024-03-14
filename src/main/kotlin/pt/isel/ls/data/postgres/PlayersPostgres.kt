package pt.isel.ls.data.postgres

import pt.isel.ls.data.PlayerStorage
import pt.isel.ls.utils.postgres.toPlayer
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.postgres.useWithRollback
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import kotlin.NoSuchElementException

class PlayersPostgres(private val conn: Connection): PlayerStorage {
    override fun create(name: String, email: String): Player = conn.useWithRollback {
        val statement = it.prepareStatement(
            """INSERT INTO PLAYERS(player_name, email) VALUES (?, ?)""".trimIndent(),
            Statement.RETURN_GENERATED_KEYS
        ).apply {
            setString(1, name)
            setString(2, email)
        }

        if (statement.executeUpdate() == 0)
            throw SQLException("Creating user failed, no rows affected")

        val generatedKeys = statement.generatedKeys

        if(generatedKeys.next())
            return get(generatedKeys.getInt(1))

        throw SQLException("Creating user failed, no ID was created")
    }

    override fun get(id: Int): Player = conn.useWithRollback {
        val statement = it.prepareStatement(
            """
            select * from players where player_id = ?    
            """.trimIndent()
        ).apply {
            setInt(1, id)
        }

        val resultSet = statement.executeQuery()

        if(resultSet.next())
            return resultSet.toPlayer()
        throw NoSuchElementException("Could not get user, id $id was not found")
    }
}