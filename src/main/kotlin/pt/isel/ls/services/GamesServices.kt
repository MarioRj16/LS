package pt.isel.ls.services

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Game
import java.util.*

open class GamesServices(data: Data) : ServicesSchema(data) {
    fun searchGames(
        searchParameters: GameSearch,
        token: UUID,
        skip: Int,
        limit: Int,
    ): List<Game> =
        withAuthorization(token) {
            data.games.search(searchParameters, limit, skip)
        }

    fun createGame(
        gameInput: GameCreate,
        token: UUID,
    ): Int =
        withAuthorization(token) {
            val game = data.games.create(gameInput)
            return@withAuthorization game.id
        }

    fun getGame(
        id: Int,
        token: UUID,
    ): Game =
        withAuthorization(token) {
            return@withAuthorization data.games.get(id)
        }
}
