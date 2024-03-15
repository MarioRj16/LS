package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Game
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch

class GamesServices(private val db: Storage) {
    fun searchGames(input: String):List<Game> {
        val gameInput = Json.decodeFromString<GameSearch>(input)
        if (gameInput.skip > gameInput.limit) throw IllegalArgumentException("limit must be higher than skip")
        return db.games.search(gameInput.developer, gameInput.genres,gameInput.limit,gameInput.skip)
    }

    fun createGame(input: String):Int? {
        val gameInput = Json.decodeFromString<GameCreate>(input)
        val game=db.games.create(gameInput.name, gameInput.developer, gameInput.genres)
        return game.id
    }

    fun getGame(id: Int?) :Game{
        require(id!=null){"id"}
        return db.games.getById(id)
    }
}