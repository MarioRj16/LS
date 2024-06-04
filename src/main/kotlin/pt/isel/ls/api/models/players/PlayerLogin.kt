package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.values.Email

@Serializable
class PlayerLogin private constructor(
    val email: Email,
    val password: String
){
    companion object {
        operator fun invoke(player: Player): PlayerLogin {
            return PlayerLogin( player.email,player.password)
        }
    }
}
