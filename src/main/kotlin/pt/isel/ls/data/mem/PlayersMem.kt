package pt.isel.ls.data.mem

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ConflictException
import java.util.*

class PlayersMem(private val players: DataMemTable<Player> = DataMemTable()) : PlayersData {

    override fun create(
        playerCreate: PlayerCreate,
    ): Player {
        val (name, email) = playerCreate
        if (emailExists(email)) throw ConflictException("The given email is not unique")
        val player = Player(players.nextId.get(), name, email)
        players.table[players.nextId.get()] = player
        return player
    }

    override fun get(id: Int): Player? = players.table[id]

    override fun get(token: UUID): Player? =
        players.table.values.find { it.token == token }

    override fun get(email: String): Player? = players.table.values.find { it.email == email }

    private fun emailExists(email: String): Boolean = players.table.any { it.value.email == email }
}
