package pt.isel.ls.services

import kotlinx.serialization.json.Json
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Game
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
open class GamesServices(private val db: Storage):ServicesSchema() {
    fun searchGames(input: String,authorization:String?,skip:Int?,limit:Int?):List<Game> {
        bearerToken(authorization,db)
        val gameInput = Json.decodeFromString<GameSearch>(input)
        return db.games.search(
            gameInput.developer,
            gameInput.genres,
            limit ?: DEFAULT_LIMIT,
            skip ?: DEFAULT_SKIP
        )
    }

    fun createGame(input: String,authorization:String?):Int {
        bearerToken(authorization,db)
        val gameInput = Json.decodeFromString<GameCreate>(input)
        val game=db.games.create(gameInput.name, gameInput.developer, gameInput.genres)
        return game.id
    }

    fun getGame(id: Int?,authorization:String?) :Game{
        require(id!=null){"id"}
        bearerToken(authorization,db)
        return db.games.getById(id)
    }
}