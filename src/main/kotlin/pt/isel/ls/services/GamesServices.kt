package pt.isel.ls.services

import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Game

open class GamesServices(internal val db: Data) : ServicesSchema() {
    fun searchGames(
        searchParameters: GameSearch,
        authorization: String?,
        skip: Int?,
        limit: Int?,
    ): List<Game> {
        bearerToken(authorization, db)
        return db.games.search(
            searchParameters,
            limit ?: DEFAULT_LIMIT,
            skip ?: DEFAULT_SKIP,
        )
    }

    fun createGame(
        gameInput: GameCreate,
        authorization: String?,
    ): Int {
        bearerToken(authorization, db)
        val game = db.games.create(gameInput)
        return game.id
    }

    fun getGame(
        id: Int?,
        authorization: String?,
    ): Game {
        requireNotNull(id) { "Invalid argument id can't be null" }
        bearerToken(authorization, db)
        return db.games.get(id)
    }
}
