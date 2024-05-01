package pt.isel.ls.api.models.players

import java.util.*
import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.serializers.UUIDSerializer

@Serializable
class PlayerResponse private constructor(
    @Serializable(with = UUIDSerializer::class)
    val token: UUID,
    val playerId: Int,
) {
    companion object {
        operator fun invoke(player: Player): PlayerResponse {
            return PlayerResponse(player.token, player.id)
        }
    }
}
