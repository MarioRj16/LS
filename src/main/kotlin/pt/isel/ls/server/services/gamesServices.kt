package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Data.Storage
import pt.isel.ls.Domain.GameCreate
import pt.isel.ls.Domain.GameSearch

class GamesServices(private val db: Storage) {
    fun searchGames(input: String) {
        val gameInput = Json.decodeFromString<GameSearch>(input)
        db.games.search(gameInput.developer, gameInput.genres)
    }

    fun createGame(input: String) {
        val gameInput = Json.decodeFromString<GameCreate>(input)
        db.games.create(gameInput.name, gameInput.developer, gameInput.genres)
    }

    fun getGame(id: Int?) {
        require(id!=null)
        db.games.getByID(id)
        /**
         * NEED TO ADD getByID function to GamesMEM
         */
    }
}