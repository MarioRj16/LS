package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.api.models.games.GameResponseMinimized
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Session

@Serializable
class SessionResponse private constructor(
    val id: Int,
    val game: GameResponseMinimized,
    val currentCapacity: Int,
    val capacity: Int,
    val date: LocalDateTime,
    val isOpen: Boolean,
) {
    companion object {
        operator fun invoke(session: Session, game: Game): SessionResponse {
            return SessionResponse(
                session.id,
                GameResponseMinimized(game),
                session.players.count(),
                session.maxCapacity,
                session.startingDate,
                session.state,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionResponse

        if (id != other.id) return false
        if (game != other.game) return false
        if (currentCapacity != other.currentCapacity) return false
        if (capacity != other.capacity) return false
        if (date != other.date) return false
        if (isOpen != other.isOpen) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + game.hashCode()
        result = 31 * result + capacity
        result = 31 * result + currentCapacity
        result = 31 * result + date.hashCode()
        return result
    }
}
