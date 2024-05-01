package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Player

@Serializable
class PlayerListResponse private constructor(
    val players: List<PlayerListElement>,
    val total: Int,
) {
    companion object {
        operator fun invoke(players: List<Player>): PlayerListResponse {
            return PlayerListResponse(
                players.map { player -> PlayerListElement(player) },
                players.size,
            )
        }
    }
}
