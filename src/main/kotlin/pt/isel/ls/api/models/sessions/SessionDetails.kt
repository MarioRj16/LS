package pt.isel.ls.api.models.sessions

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.domain.Session

@Serializable
class SessionDetails private constructor(
    val id: Int,
    val game: Int,
    val host: Int,
    val capacity: Int,
    val date: LocalDateTime,
    val players: Set<PlayerDetails>
){
    companion object {
        operator fun invoke(session: Session): SessionDetails {
            return SessionDetails(
                session.id,
                session.gameId,
                session.hostId,
                session.maxCapacity,
                session.startingDate,
                session.players.map { PlayerDetails(it) }.toSet()
            )
        }
    }
}
