package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.api.models.GenreDetails
import pt.isel.ls.domain.Game

@Serializable
class GameDetails private constructor(
    val id: Int,
    val name: String,
    val developer: String,
    val genres: Set<GenreDetails>,
) {
    companion object {
        operator fun invoke(game: Game): GameDetails {
            val genres = game.genres.map { GenreDetails(it) }.toSet()
            return GameDetails(game.id, game.name, game.developer, genres)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameDetails

        if (id != other.id) return false
        if (name != other.name) return false
        if (developer != other.developer) return false
        if (genres != other.genres) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + developer.hashCode()
        result = 31 * result + genres.hashCode()
        return result
    }
}
