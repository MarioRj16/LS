package pt.isel.ls.models

import kotlinx.serialization.Serializable

@Serializable
data class GameAndGenre(val game: Int, val genre: Int)