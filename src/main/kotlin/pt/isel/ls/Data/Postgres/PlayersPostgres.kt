package pt.isel.ls.Data.Postgres

import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Data.Postgres.utils.toPlayer
import pt.isel.ls.Domain.Player
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import kotlin.NoSuchElementException

class PlayersPostgres(private val conn: Connection): PlayerStorage {
    override fun create(name: String, email: String): Player = conn.use {
        val stm = it.prepareStatement(
            """INSERT INTO PLAYERS(player_name, email) VALUES (?, ?)""".trimIndent(),
            Statement.RETURN_GENERATED_KEYS
        )
        stm.setString(1, name)
        stm.setString(2, email)

        if (stm.executeUpdate() == 0)
            throw SQLException("Creating user failed, no rows affected")

        val generatedKeys = stm.generatedKeys

        if(generatedKeys.next())
            return get(generatedKeys.getInt(1))

        throw SQLException("Creating user failed, no ID was created")
    }

    override fun get(id: Int): Player = conn.use {
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