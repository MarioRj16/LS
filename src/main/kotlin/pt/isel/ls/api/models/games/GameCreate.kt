package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.generateRandomString

@Serializable
data class GameCreate(val name: String, val developer: String, val genres: Set<Int>) {
    companion object Factory {
        fun create(
            name: String = generateRandomString(),
            developer: String = generateRandomString(),
            genres: Set<Int>,
        ): GameCreate {
            return GameCreate(name, developer, genres)
        }
    }
}
