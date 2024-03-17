package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.bearerToken
import pt.isel.ls.utils.exceptions.AuthorizationException


class PlayerServices(private val db:Storage) {
    fun createPlayer(input: String) :Player{
        val playerInput = Json.decodeFromString<PlayerCreate>(input)
        return db.players.create(playerInput.name, playerInput.email)
    }

    fun getPlayer(id: Int?,authorization:String?):Player {
        require(id!=null){"id"}
        val ownId=bearerToken(authorization,db).id
        if(ownId!=id) throw AuthorizationException(
            "You dont have authorization to see this player, instead you can see your own id $ownId")
        val player=db.players.get(id)
        return player
    }

}