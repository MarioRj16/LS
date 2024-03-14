package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Domain.Player
import java.util.*

class PlayersMem(private val players: DBTableMem<Player>): PlayerStorage {
    override fun create(name: String, email: String): Player {
        // TODO: Add some regex to verify the email

        require('@' in email){"The email has to contain a '@'"}
        require(players.table.none { it.value.email == email }){" The email has to be unique"}
        // TODO: Find out if we can ignore token collisions
        val obj = Player(
            id = players.nextId,
            name = name,
            email = email,
            token = UUID.randomUUID()
        )
        players.table[players.nextId] = obj
        return obj
    }

    override fun search(id: Int): Player? {
        return players.table[id]
    }

}