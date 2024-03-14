package pt.isel.ls.domain

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.UUIDSerializer
import java.util.*

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val email: String,
    @Serializable(with = UUIDSerializer::class)
    val token: UUID
)