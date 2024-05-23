package pt.isel.ls.domain

import java.util.*
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.serializers.UUIDSerializer
import pt.isel.ls.utils.values.Email
import pt.isel.ls.utils.values.Password

data class Player(
    val id: Int,
    val name: String,
    val email: Email,
    val password: Password,
    @Serializable(with = UUIDSerializer::class)
    val token: UUID = UUID.randomUUID(),
) {
    init {
        require(id > 0) { "ID must be a positive Int\nID=$id" }
        require(name.isNotBlank()) { "Name cannot be blank" }
    }
}
