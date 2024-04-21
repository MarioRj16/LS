package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable

/**
 * TODO If we can change genres to GenreClass
 */
@Serializable
data class GameSearch(val name: String?, val developer: String?, val genres: Set<Int>)
