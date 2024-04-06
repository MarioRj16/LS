package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.UUIDSerializer
import java.util.*

@Serializable
data class PlayerResponse(
    @Serializable(with = UUIDSerializer::class)
    val token: UUID,
    val playerId: Int,
)
