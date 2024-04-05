package pt.isel.ls.api.models

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.emailIsValid

@Serializable
data class PlayerCreate(val name: String, val email: String)
