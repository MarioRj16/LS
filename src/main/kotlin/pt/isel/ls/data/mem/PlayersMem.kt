package pt.isel.ls.data.mem

import java.util.*
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerListElement
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.utils.values.Email

class PlayersMem(private val players: DataMemTable<Player> = DataMemTable()) : PlayersData {

    override fun create(
        playerCreate: PlayerCreate,
    ): Player {
        val (name, email, password) = playerCreate
        val player = Player(players.nextId.get(), name, email, password)
        players.table[players.nextId.get()] = player
        return player
    }

    override fun get(id: Int): Player? = players.table[id]

    override fun get(token: UUID): Player? = players.table.values.find { it.token == token }

    override fun get(email: Email): Player? = players.table.values.find { it.email == email }

    override fun get(username: String): Player? = players.table.values.find { it.name == username }

    override fun search(searchParameters: PlayerSearch, skip: Int, limit: Int): PaginatedResponse<PlayerListElement> {
        val username = searchParameters.username
        val list = if (username != null) {
            players.table.values.filter { it.name.startsWith(username) }
        } else {
            players.table.values.toList()
        }.map { PlayerListElement(it) }
        return PaginatedResponse.fromList(list, skip, limit)
    }
}
