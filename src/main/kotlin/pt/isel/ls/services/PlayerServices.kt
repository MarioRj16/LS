package pt.isel.ls.services

import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException

open class PlayerServices(internal val db: Data) : ServicesSchema() {
    fun createPlayer(playerCreate: PlayerCreate): Player {
        return db.players.create(playerCreate)
    }

    fun getPlayer(
        playerId: Int?,
        authorization: String?,
    ): Player {
        requireNotNull(playerId) { "Invalid argument id can't be null" }
        val ownId = bearerToken(authorization, db).id
        if (ownId != playerId) {
            throw ForbiddenException(
                "You dont have authorization to see this player, instead you can see your own id $ownId",
            )
        }
        return db.players.get(playerId)
    }
}
