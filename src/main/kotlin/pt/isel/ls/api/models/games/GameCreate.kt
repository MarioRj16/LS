package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable

@Serializable
data class GameCreate(val name: String, val developer: String, val genres: Set<Int>)
