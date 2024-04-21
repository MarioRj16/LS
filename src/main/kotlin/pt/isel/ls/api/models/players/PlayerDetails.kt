package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.Email

@Serializable
class PlayerDetails private constructor(val id: Int, val name: String, val email: Email) {

    companion object {
        operator fun invoke(player: Player): PlayerDetails {
            return PlayerDetails(player.id, player.name, player.email)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerDetails

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }
}
