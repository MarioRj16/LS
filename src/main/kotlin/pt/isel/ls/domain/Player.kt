package pt.isel.ls.domain

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.UUIDSerializer
import pt.isel.ls.utils.emailIsValid
import java.util.*

data class Player(
    val id: Int,
    val name: String,
    val email: String,
    @Serializable(with = UUIDSerializer::class)
    val token: UUID = UUID.randomUUID(),
) {
    init {
        require(id > 0) { "ID must be a positive Int\nID=$id" }
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(emailIsValid(email)) { "Email must be in valid format\nemail=$email" }
    }
}
