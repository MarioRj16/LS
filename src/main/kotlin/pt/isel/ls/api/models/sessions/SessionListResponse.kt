package pt.isel.ls.api.models.sessions

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.PaginateResponse

@Serializable
class SessionListResponse private constructor(
    val sessions: List<SessionResponse>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int
) {
    companion object {
        operator fun invoke(sessionsResponse: PaginateResponse<SessionResponse>): SessionListResponse {
            val (session, hasNext, hasPrevious) = sessionsResponse
            return SessionListResponse(session, hasNext, hasPrevious, session.size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionListResponse

        if (sessions != other.sessions) return false
        if (hasNext != other.hasNext) return false
        if (hasPrevious != other.hasPrevious) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sessions.hashCode()
        result = 31 * result + hasNext.hashCode()
        result = 31 * result + hasPrevious.hashCode()
        result = 31 * result + total
        return result
    }
}
