package pt.isel.ls.Data.Postgres

import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Domain.Player
import pt.isel.ls.utils.exceptions.ObjectDoesNotExistException
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*

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
            return get(generatedKeys.getInt(1))!!

        throw SQLException("Creating user failed, no ID was created")
    }

    override fun get(id: Int): Player? = conn.use {
        val stm = it.prepareStatement(
            """
            select * from players where id = ?    
            """.trimIndent()
        )

        stm.setInt(1, id)
        val rs = stm.executeQuery()

        // TODO: Should we be using labels or idx to get to the value of a column?
        if(rs.next())
            return Player(
                id = rs.getInt(1),
                name = rs.getString(2),
                email = rs.getString(3),
                token = UUID.fromString(rs.getString(4))
            )
        throw ObjectDoesNotExistException("Could not get user, id $id was not found")
    }

    override fun emailExists(email: String): Boolean = conn.use {
        val stm = it.prepareStatement(
            """SELECT * FROM PLAYERS WHERE email = ?"""
        )
        stm.setString(1, email)
        val rs = stm.executeQuery()
        return rs.next()
    }
}