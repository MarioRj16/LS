package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Domain.PlayerCreate
import pt.isel.ls.Data.Storage


class PlayerServices(private val db:Storage) {
    fun createPlayer(input: String) {
        val playerInput = Json.decodeFromString<PlayerCreate>(input)
        db.players.create(playerInput.name, playerInput.email)
    }

    fun getPlayer(id: Int?) {
        require(id!=null)
       db.players.search(id)
    }

}