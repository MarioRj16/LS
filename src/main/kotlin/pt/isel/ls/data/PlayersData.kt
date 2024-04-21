package pt.isel.ls.data

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.Email
import java.util.*

interface PlayersData {
    fun create(playerCreate: PlayerCreate): Player

    fun get(id: Int): Player?

    fun get(token: UUID): Player?

    fun get(email: Email): Player?

    fun get(username: String): Player?
}
