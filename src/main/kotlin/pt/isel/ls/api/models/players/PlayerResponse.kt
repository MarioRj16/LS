package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.UUIDSerializer
import java.util.*

@Serializable
class PlayerResponse private constructor(
    @Serializable(with = UUIDSerializer::class)
    val token: UUID,
    val playerId: Int,
){
    companion object {
        operator fun invoke(player: Player): PlayerResponse {
            return PlayerResponse(player.token, player.id)
        }

    }
}
