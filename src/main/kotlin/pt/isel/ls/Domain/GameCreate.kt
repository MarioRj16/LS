package pt.isel.ls.Domain

import kotlinx.serialization.Serializable

@Serializable
data class GameCreate(val name: String, val developer: String, val genres: List<String>)