package pt.isel.ls.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerSession(val player: Int, val gamingSession: Int)
