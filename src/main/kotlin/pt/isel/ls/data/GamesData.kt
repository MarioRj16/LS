package pt.isel.ls.data

import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre

interface GamesData {
    fun create(name: String, developer: String, genres: Set<Genre>): Game

    fun get(name: String): Game?

    fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): List<Game>

    fun get(id: Int): Game?

    fun genresExist(genreIds: Set<Int>): Boolean

    fun getGenres(genreIds: Set<Int>): Set<Genre>

    fun getAllGenres():Set<Genre>
}
