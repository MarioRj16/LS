package pt.isel.ls.services

import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException

open class PlayerServices(internal val db: Data) : ServicesSchema(db) {
    fun createPlayer(playerCreate: PlayerCreate): Player {
        return db.players.create(playerCreate)
    }

    fun getPlayer(
        playerId: Int?,
        authorization: String?,
    ): Player = withAuthorization(authorization){ user ->
        requireNotNull(playerId) { "Invalid argument id can't be null" }
        if (user.id != playerId) {
            throw ForbiddenException(
                "You dont have authorization to see this player, instead you can see your own id ${user.id}",
            )
        }
        return@withAuthorization db.players.get(playerId)
    }
}
