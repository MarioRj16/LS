package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Genre

@Serializable
data class GameCreate(val name: String, val developer: String, val genres: Set<Genre>) {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
        require(developer.isNotBlank()) { "Developer must not be blank" }
        require(genres.isNotEmpty()) { "Genres must not be empty" }
    }
}
