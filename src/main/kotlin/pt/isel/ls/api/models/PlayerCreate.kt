package pt.isel.ls.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerCreate(val name: String, val email: String)
