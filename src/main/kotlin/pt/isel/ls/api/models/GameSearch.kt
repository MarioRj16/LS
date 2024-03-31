package pt.isel.ls.api.models

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Genre

/**
 * TODO If we can change genres to GenreClass
 */
@Serializable
data class GameSearch(val developer: String? = null, val genres: Set<Genre>? = null)
