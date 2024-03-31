package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.ForbiddenException


open class PlayerServices(internal val db: Storage) : ServicesSchema() {
    fun createPlayer(input: String): Player {
        val playerInput = Json.decodeFromString<PlayerCreate>(input)
        return db.players.create(playerInput.name, playerInput.email)
    }

    fun getPlayer(id: Int?, authorization: String?): Player {
        requireNotNull(id) { "Invalid argument id can't be null" }
        val ownId = bearerToken(authorization, db).id
        if (ownId != id) throw ForbiddenException(
            "You dont have authorization to see this player, instead you can see your own id $ownId"
        )
        return db.players.get(id)
    }

}