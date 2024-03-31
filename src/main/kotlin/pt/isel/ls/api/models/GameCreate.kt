package pt.isel.ls.api.models

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Genre

@Serializable
data class GameCreate(val name: String, val developer: String, val genres: Set<Genre>)
