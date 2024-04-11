package pt.isel.ls.api.models

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Genre

@Serializable
class GenreDetails private constructor(
    val genreId: Int,
    val name: String,
) {
    companion object {
        operator fun invoke(genre: Genre): GenreDetails {
            return GenreDetails(genre.genreId, genre.genreName)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreDetails

        if (genreId != other.genreId) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = genreId
        result = 31 * result + name.hashCode()
        return result
    }
}
