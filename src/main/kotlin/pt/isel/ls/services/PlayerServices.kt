package pt.isel.ls.services

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.api.models.players.PlayerDetails
import pt.isel.ls.api.models.players.PlayerListResponse
import pt.isel.ls.api.models.players.PlayerResponse
import pt.isel.ls.api.models.players.PlayerSearch
import pt.isel.ls.data.Data
import pt.isel.ls.utils.exceptions.BadRequestException
import java.util.*

open class PlayerServices(internal val db: Data) : ServicesSchema(db) {
    fun createPlayer(playerCreate: PlayerCreate): PlayerResponse {
        if (db.players.get(playerCreate.name) != null) {
            throw BadRequestException("The given username is not unique")
        }

        if (db.players.get(playerCreate.email) != null) {
            throw BadRequestException("The given email is not unique")
        }
        val player = db.players.create(playerCreate)
        return PlayerResponse(player)
    }

    fun getPlayer(
        playerId: Int,
        token: UUID,
    ): PlayerDetails = withAuthorization(token) {
        val player = db.players.get(playerId)
            ?: throw NoSuchElementException("No player with id $playerId was found")
        return@withAuthorization PlayerDetails(player)
    }

    fun searchPlayers(
        searchParameters: PlayerSearch,
        token: UUID,
        skip: Int,
        limit: Int,
    ): PlayerListResponse = withAuthorization(token) {
        val players = db.players.search(searchParameters, skip, limit)
        return@withAuthorization PlayerListResponse(players)
    }
}
