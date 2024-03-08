package pt.isel.ls.Domain

import kotlinx.serialization.Serializable

@Serializable
data class GameAndGenre(val game: Int, val genre: String)