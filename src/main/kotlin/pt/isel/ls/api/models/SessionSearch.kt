package pt.isel.ls.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SessionSearch(val game: Int,
                         val date:LocalDateTime? = null ,
                         val state:Boolean? = null ,
                         val playerId: Int? = null
)

/**
 * gid - game identifier
 * date - session date (optional)
 * state - open or close (optional)
 * pid - player identifier (optional)
 */