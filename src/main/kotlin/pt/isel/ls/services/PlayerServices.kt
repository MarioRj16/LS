package pt.isel.ls.services

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException
import java.util.*

open class PlayerServices(internal val db: Data) : ServicesSchema(db) {
    fun createPlayer(playerCreate: PlayerCreate): Player {
        return db.players.create(playerCreate)
    }

    fun getPlayer(
        playerId: Int,
        token: UUID,
    ): Player = withAuthorization(token) { user ->
        if (user.id != playerId) {
            throw ForbiddenException(
                "You dont have authorization to see this player, instead you can see your own id ${user.id}",
            )
        }
        return@withAuthorization db.players.get(playerId)
    }
}
