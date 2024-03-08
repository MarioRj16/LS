package pt.isel.ls.Domain

import kotlinx.serialization.Serializable

@Serializable
data class PlayerCreate(val name: String, val email: String)