package pt.isel.ls.services

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.api.models.players.PlayerResponse
import pt.isel.ls.data.Data
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.exceptions.ForbiddenException
import java.util.*

open class PlayerServices(internal val db: Data) : ServicesSchema(db) {
    fun createPlayer(playerCreate: PlayerCreate): PlayerResponse {
        if (db.players.get(playerCreate.email) != null) {
            throw ConflictException("The given email is not unique")
        }
        val player = db.players.create(playerCreate)
        return PlayerResponse(player)
    }

    fun getPlayer(
        playerId: Int,
        token: UUID,
    ): PlayerDetails = withAuthorization(token) { user ->
        if (user.id != playerId) {
            throw ForbiddenException(
                "You dont have authorization to see this player, instead you can see your own id ${user.id}",
            )
        }
        val player = db.players.get(playerId)
            ?: throw NoSuchElementException("No player with id $playerId was found")
        return@withAuthorization PlayerDetails(player)
    }
}
