package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Domain.Player
import java.util.*

class PlayersMem(val schema: DataMemSchema): PlayerStorage {
    override fun create(name: String, email: String): Player {
        // TODO: Add some regex to verify the email
        require('@' in email){"The email has to contain a '@'"}
        require(schema.playersDB.none { it.value.email == email }){" The email has to be unique"}
        // TODO: Find out if we can ignore token collisions
        val obj = Player(
            id = schema.playersNextId,
            name = name,
            email = email,
            token = UUID.randomUUID()
        )
        schema.playersDB[schema.playersNextId++] = obj
        return obj
    }

    override fun search(id: Int): Player? {
        return schema.playersDB[id]
    }

}