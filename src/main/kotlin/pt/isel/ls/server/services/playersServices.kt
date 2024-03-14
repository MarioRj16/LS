package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Domain.PlayerCreate
import pt.isel.ls.Data.Storage
import pt.isel.ls.Domain.Player
import pt.isel.ls.utils.exceptions.NotFoundException


class PlayerServices(private val db:Storage) {
    fun createPlayer(input: String) :Player{
        val playerInput = Json.decodeFromString<PlayerCreate>(input)
        return db.players.create(playerInput.name, playerInput.email)
    }

    fun getPlayer(id: Int?):Player {
        require(id!=null){"id"}
        val player=db.players.search(id) ?: throw NotFoundException("player")
        return player
    }

}