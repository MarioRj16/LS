package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Game
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.utils.bearerToken

class GamesServices(private val db: Storage) {
    fun searchGames(input: String,authorization:String?):List<Game> {
        bearerToken(authorization,db)
        val gameInput = Json.decodeFromString<GameSearch>(input)
        return db.games.search(gameInput.developer, gameInput.genres,gameInput.limit,gameInput.skip)
    }

    fun createGame(input: String,authorization:String?):Int {
        bearerToken(authorization,db)
        val gameInput = Json.decodeFromString<GameCreate>(input)
        val game=db.games.create(gameInput.name, gameInput.developer, gameInput.genres)
        return game.id
    }

    fun getGame(id: Int?,authorization:String?) :Game{
        bearerToken(authorization,db)
        require(id!=null){"id"}
        return db.games.getById(id)
    }
}