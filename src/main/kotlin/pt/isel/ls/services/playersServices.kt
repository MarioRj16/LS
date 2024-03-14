package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.NotFoundException


class PlayerServices(private val db:Storage) {
    fun createPlayer(input: String) :Player{
        val playerInput = Json.decodeFromString<PlayerCreate>(input)
        return db.players.create(playerInput.name, playerInput.email)
    }

    fun getPlayer(id: Int?):Player {
        require(id!=null){"id"}
        val player=db.players.get(id) ?: throw NotFoundException("player")
        return player
    }

}