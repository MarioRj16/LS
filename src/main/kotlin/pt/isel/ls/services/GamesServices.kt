package pt.isel.ls.services

import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Game

open class GamesServices(data: Data) : ServicesSchema(data) {
    fun searchGames(
        searchParameters: GameSearch,
        authorization: String?,
        skip: Int?,
        limit: Int?,
    ): List<Game> =
        withAuthorization(authorization){
             data.games.search(
                searchParameters,
                limit ?: DEFAULT_LIMIT,
                skip ?: DEFAULT_SKIP,
            )
        }

    fun createGame(
        gameInput: GameCreate,
        authorization: String?,
    ): Int =
        withAuthorization(authorization){
            val game = data.games.create(gameInput)
            return@withAuthorization game.id
    }

    fun getGame(
        id: Int?,
        authorization: String?,
    ): Game =
        withAuthorization(authorization) {
            requireNotNull(id) { "Invalid argument id can't be null" }
            return@withAuthorization data.games.get(id)
    }
}
