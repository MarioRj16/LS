package pt.isel.ls.data.mem

import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.emailIsValid
import pt.isel.ls.utils.exceptions.ConflictException
import java.util.*

class PlayersMem(private val players: DataMemTable<Player> = DataMemTable()) : PlayersData {
    override fun create(name: String, email: String): Player {
        require(emailIsValid(email)) { "The given email is not in the right format" }
        if (emailExists(email)) throw ConflictException("The given email is not unique")
        val obj = Player(players.nextId.get(), name, email)
        players.table[players.nextId.get()] = obj
        return obj
    }

    override fun get(id: Int): Player =
        players.table[id] ?: throw NoSuchElementException("No player with id $id was found")

    override fun getByToken(token: UUID): Player =
         players.table.values.firstOrNull { it.token == token }
            ?: throw NoSuchElementException("No player with token $token was found")

    private fun emailExists(email: String): Boolean = players.table.any { it.value.email == email }
}