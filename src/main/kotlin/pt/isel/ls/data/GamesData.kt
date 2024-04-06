package pt.isel.ls.data

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.domain.Game

interface GamesData {
    fun create(gameCreate: GameCreate): Game

    fun get(name: String): Game

    fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): List<Game>

    fun get(id: Int): Game
}
