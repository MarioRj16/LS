package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.PaginatedResponse

@Serializable
class PlayerListResponse private constructor(
    val players: List<PlayerListElement>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int,
) {
    companion object {
        operator fun invoke(paginatedPlayers: PaginatedResponse<PlayerListElement>): PlayerListResponse {
            val (player, hasNext, hasPrevious) = paginatedPlayers
            return PlayerListResponse(player, hasNext, hasPrevious, player.size)
        }
    }
}
