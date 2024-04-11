package pt.isel.ls.utils.factories

import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.generateRandomString
import kotlin.random.Random

class GameFactory(
    private val games: GamesData,
    private val genresDB: Map<Int, Genre> = setOf(
        Genre(1, "Action"),
        Genre(2, "Adventure"),
        Genre(3, "RPG"),
        Genre(4, "Simulation"),
        Genre(5, "Strategy"),
    ).associateBy { it.genreId },
) {
    private val developers = listOf("Developer1", "Developer2", "Developer3")

    fun createRandomGame(): Game {
        val randomDeveloper = developers.random()
        var randomName = generateRandomString()
        while (true) {
            games.get(randomName)
                ?: return games.create(
                    randomName,
                    randomDeveloper,
                    List(Random.nextInt(1, 4)) { genresDB.values.random() }.toSet(),
                )
            randomName = generateRandomString()
        }
    }

    private fun generateRandomGenres(): Set<Genre> {
        return List(Random.nextInt(1, genresDB.size + 1)) { genresDB.values.random() }.toSet()
    }
}
