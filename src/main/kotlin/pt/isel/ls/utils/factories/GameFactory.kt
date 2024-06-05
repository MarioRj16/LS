package pt.isel.ls.utils.factories

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.data.GamesData
import pt.isel.ls.data.GenresData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.generateRandomString
import java.util.*

class GameFactory(
    private val games: GamesData,
    genresDB: GenresData,
) {
    private val genreFactory = GenresFactory(genresDB)

    fun createRandomGame(
        name: String? = null,
        developer: String? = null,
        genres: Set<Genre>? = null,
    ): Game {
        // Name of game is a UUID turned to string to make sure the risk of collision is as low as possible
        val gameName = name ?: UUID.randomUUID().toString()
        val gameDeveloper = developer ?: generateRandomString()
        val gameGenres = genres ?: genreFactory.random()
        val gameCreate = GameCreate(gameName, gameDeveloper, gameGenres.map { it.genreId }.toSet())
        return games.create(gameCreate, gameGenres)
    }
}
