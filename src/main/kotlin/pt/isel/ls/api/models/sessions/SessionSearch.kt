package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.utils.Email

@Serializable
data class SessionSearch(
    val game: Int? = null,
    val date: LocalDateTime? = null,
    val state: Boolean? = null,
    val playerEmail: Email? = null,
)

/**
 * gid - game identifier
 * date - session date (optional)
 * state - open or close (optional)
 * pid - player identifier (optional)
 */
