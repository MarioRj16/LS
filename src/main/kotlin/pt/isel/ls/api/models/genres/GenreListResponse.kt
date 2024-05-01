package pt.isel.ls.api.models.genres

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Genre

@Serializable
class GenreListResponse private constructor(
    val genres: List<GenreDetails>,
    val total: Int = genres.size
) {
    companion object {
        operator fun invoke(genres: Set<Genre>): GenreListResponse {
            return GenreListResponse(genres.map { genre -> GenreDetails(genre) }, genres.size)
        }
    }
}