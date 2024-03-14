package pt.isel.ls.api.models;

import kotlinx.serialization.Serializable

@Serializable
data class GameSearch(val developer: String? = null, val genres: Set<String>?  = null)