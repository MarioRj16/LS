package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Game

@Serializable
class GameResponseMinimized private constructor(
    val id: Int,
    val name: String,
) {
    companion object {
        operator fun invoke(game: Game): GameResponseMinimized {
            return GameResponseMinimized(
                game.id,
                game.name,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameResponse

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