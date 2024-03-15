package pt.isel.ls.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SessionSearch(val game: Int,
                         val date:LocalDateTime? = null ,
                         val state:Boolean? = null ,
                         val playerId: Int? = null ,
                         val limit:Int = 30 ,
                         val skip:Int = 0
)

/**
 * gid - game identifier
 * date - session date (optional)
 * state - open or close (optional)
 * pid - player identifier (optional)
 */