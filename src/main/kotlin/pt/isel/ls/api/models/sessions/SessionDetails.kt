package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.api.models.games.GameResponse
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Session

@Serializable
class SessionDetails private constructor(
    val id: Int,
    val game: GameResponse,
    val host: Int,
    val capacity: Int,
    val date: LocalDateTime,
    val players: Set<PlayerDetails>,
    val isOpen: Boolean,
) {
    companion object {
        operator fun invoke(session: Session, game: Game): SessionDetails {
            return SessionDetails(
                session.id,
                GameResponse(game),
                session.hostId,
                session.maxCapacity,
                session.startingDate,
                session.players.map { PlayerDetails(it) }.toSet(),
                session.state,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SessionDetails) return false

        if (id != other.id) return false
        if (game != other.game) return false
        if (host != other.host) return false
        if (capacity != other.capacity) return false
        if (date != other.date) return false
        if (players != other.players) return false
        if (isOpen != other.isOpen) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + game.hashCode()
        result = 31 * result + host
        result = 31 * result + capacity
        result = 31 * result + date.hashCode()
        result = 31 * result + players.hashCode()
        return result
    }
}
