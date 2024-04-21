package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.Email

@Serializable
data class PlayerCreate(val name: String, val email: Email) {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
    }
}
