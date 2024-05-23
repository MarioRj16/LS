package pt.isel.ls.data

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.PaginateResponse

interface GamesData {
    fun create(gameInput: GameCreate, genres: Set<Genre>): Game

    fun get(name: String): Game?

    fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): PaginateResponse<Game>

    fun get(id: Int): Game?

}
