package pt.isel.ls.Domain;

import kotlinx.serialization.Serializable

@Serializable
data class GameSearch(val developer: String? = null, val genres: Set<String>?  = null)