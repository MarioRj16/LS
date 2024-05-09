package pt.isel.ls.services

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameCreateResponse
import pt.isel.ls.api.models.games.GameDetails
import pt.isel.ls.api.models.games.GameListResponse
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.Data
import pt.isel.ls.utils.exceptions.BadRequestException
import java.util.*

open class GamesServices(data: Data) : ServicesSchema(data) {
    fun searchGames(
        searchParameters: GameSearch,
        token: UUID,
        skip: Int,
        limit: Int,
    ): GameListResponse =
        withAuthorization(token) {
            val games = data.games.search(searchParameters, limit, skip)
            return@withAuthorization GameListResponse(games)
        }

    fun createGame(
        gameInput: GameCreate,
        token: UUID,
    ): GameCreateResponse =
        withAuthorization(token) {
            if (data.games.get(gameInput.name) != null) {
                throw BadRequestException("The name of a game has to be unique")
            }
            val (name, developer, genreIds) = gameInput
            if (!data.genres.genresExist(genreIds)) {
                throw IllegalArgumentException("The genres provided do not exist")
            }
            val genres = data.genres.getGenres(genreIds)
            val game = data.games.create(name, developer, genres)
            return@withAuthorization GameCreateResponse(game.id)
        }

    fun getGame(
        id: Int,
        token: UUID,
    ): GameDetails =
        withAuthorization(token) {
            val game = data.games.get(id)
                ?: throw NoSuchElementException("No game with id $id was found")
            return@withAuthorization GameDetails(game)
        }
}
