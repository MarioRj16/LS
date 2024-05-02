package pt.isel.ls.api.models.sessions

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Session

@Serializable
class SessionsOfGameResponse private constructor(val gameId: Int, val nrOfSessions: Int){
    companion object {
        operator fun invoke(gameId: Int, sessions: List<Session>): SessionsOfGameResponse{
            return SessionsOfGameResponse(gameId, sessions.size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionsOfGameResponse

        if (gameId != other.gameId) return false
        if (nrOfSessions != other.nrOfSessions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameId
        result = 31 * result + nrOfSessions.hashCode()
        return result
    }
}