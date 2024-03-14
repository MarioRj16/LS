package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Data.Storage
import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.GameCreate
import pt.isel.ls.Domain.GameSearch

class GamesServices(private val db: Storage) {
    fun searchGames(input: String):List<Game> {
        val gameInput = Json.decodeFromString<GameSearch>(input)
        return db.games.search(gameInput.developer, gameInput.genres)
    }

    fun createGame(input: String):Int? {
        val gameInput = Json.decodeFromString<GameCreate>(input)
        val game=db.games.create(gameInput.name, gameInput.developer, gameInput.genres)
        return game?.id
    }

    fun getGame(id: Int?) :Game?{
        require(id!=null){"id"}
        return db.games.getByID(id)
    }
}