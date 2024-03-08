package pt.isel.ls.Domain

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.UUIDSerializer
import java.util.*

@Serializable
data class PlayerResponse(
    @Serializable(with = UUIDSerializer::class)
    val token: UUID,
    val playerId: Int
)