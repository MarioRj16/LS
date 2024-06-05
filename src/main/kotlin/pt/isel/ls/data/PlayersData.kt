package pt.isel.ls.data

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerListElement
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.utils.values.Email
import java.util.*

interface PlayersData {
    fun create(playerCreate: PlayerCreate): Player

    fun get(id: Int): Player?

    fun get(token: UUID): Player?

    fun get(email: Email): Player?

    fun get(username: String): Player?

    fun search(searchParameters: PlayerSearch, skip: Int, limit: Int): PaginatedResponse<PlayerListElement>
}
