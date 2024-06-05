package pt.isel.ls.data.mem

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameResponse
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.PaginatedResponse

class GamesMem(
    private val gamesDB: DataMemTable<Game> = DataMemTable(),
) : GamesData {

    override fun create(
        gameInput: GameCreate,
        genres: Set<Genre>,
    ): Game {
        val (name, developer) = gameInput
        val game = Game(gamesDB.nextId.get(), name, developer, genres)
        gamesDB.table[gamesDB.nextId.get()] = game
        return game
    }

    override fun get(name: String): Game? = gamesDB.table.values.find { it.name == name }

    override fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): PaginatedResponse<GameResponse> {
        val (name, developer, genres) = searchParams
        val list = gamesDB.table.values.filter {
            (name.isNullOrBlank() || it.name.contains(name, ignoreCase = true)) &&
                (developer.isNullOrBlank() || it.developer == developer) &&
                (genres.isEmpty() || it.genres.map { i -> i.genreId }.intersect(genres).isNotEmpty())
        }.map { GameResponse(it) }
        return PaginatedResponse.fromList(list, skip, limit)
    }

    override fun get(id: Int): Game? = gamesDB.table.values.find { it.id == id }
}
