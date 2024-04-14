package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Genre(val genreId: Int, val genreName: String)
