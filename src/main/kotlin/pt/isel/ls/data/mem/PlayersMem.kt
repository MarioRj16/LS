package pt.isel.ls.data.mem

import pt.isel.ls.data.PlayerStorage
import pt.isel.ls.utils.emailIsValid
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ConflictException
import java.util.*
import kotlin.NoSuchElementException

class PlayersMem(private val players: DBTableMem<Player> = DBTableMem()): PlayerStorage {
    override fun create(name: String, email: String): Player {
        require(emailIsValid(email)){"The given email is not in the right format"}
        if (emailExists(email)) throw ConflictException("The given email is not unique")
        val obj = Player(
            id = players.nextId,
            name = name,
            email = email,
            token = UUID.randomUUID()
        )
        players.table[players.nextId] = obj
        return obj
    }

    override fun get(id: Int): Player {
        return players.table[id] ?: throw NoSuchElementException("No player with id $id was found")
    }

    override fun getByToken(token: UUID):Player{
        val player=players.table.filter { it.value.token == token }
        if (player.isEmpty()) throw NoSuchElementException("No player with token $token was found")
        return player.values.first()
    }

    private fun emailExists(email: String): Boolean {
        return players.table.any { it.value.email == email }
    }

}