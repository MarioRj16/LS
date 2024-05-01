package pt.isel.ls.data

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.Email
import java.util.*
import pt.isel.ls.api.models.players.PlayerSearch

interface PlayersData {
    fun create(playerCreate: PlayerCreate): Player

    fun get(id: Int): Player?

    fun get(token: UUID): Player?

    fun get(email: Email): Player?

    fun get(username: String): Player?

    fun search(searchParameters: PlayerSearch, skip: Int, limit: Int): List<Player>
}
