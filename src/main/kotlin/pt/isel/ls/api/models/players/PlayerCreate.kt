package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.emailIsValid

@Serializable
data class PlayerCreate(val name: String, val email: String) {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
        require(emailIsValid(email)) { "The given email is not in the right format" }
    }
}
