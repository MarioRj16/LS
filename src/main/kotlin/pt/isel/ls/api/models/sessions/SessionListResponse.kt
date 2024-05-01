package pt.isel.ls.api.models.sessions

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Session

@Serializable
class SessionListResponse private constructor(val sessions: List<SessionResponse>, val total: Int) {
    companion object {
        operator fun invoke(sessions: List<Session>): SessionListResponse {
            return SessionListResponse(sessions.map { SessionResponse(it) }, sessions.size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionListResponse

        if (sessions != other.sessions) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sessions.hashCode()
        result = 31 * result + total
        return result
    }
}
