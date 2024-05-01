package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Player

@Serializable
class PlayerListElement private constructor(
    val id: Int,
    val name: String
) {
    companion object {
        operator fun invoke(player: Player): PlayerListElement {
            return PlayerListElement(player.id, player.name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerListElement

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }
}