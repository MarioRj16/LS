package pt.isel.ls.utils.factories

import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.generateRandomString
import kotlin.random.Random

class GameFactory(private val games: GamesData) {
    private val developers = listOf("Developer1", "Developer2", "Developer3")
    private val genres =
        listOf(
            "Action",
            "Adventure",
            "RPG",
            "Strategy",
            "Puzzle",
            "Simulation",
        ).mapIndexed {
                idx, name ->
            Genre(idx, name)
        }.toSet()

    fun createRandomGame(): Game {
        val randomDeveloper = developers.random()
        val randomGenres = generateRandomGenres()
        var randomName = generateRandomString()
        try {
            while (true) {
                games.get(randomName)
                randomName = generateRandomString()
            }
        } catch (e: NoSuchElementException) {
            return games.create(randomName, randomDeveloper, randomGenres)
        }
    }

    private fun generateRandomGenres(): Set<Genre> {
        val numberOfGenres = Random.nextInt(1, 4)
        val selectedGenres = mutableSetOf<Genre>()
        repeat(numberOfGenres) {
            selectedGenres.add(genres.random())
        }
        return selectedGenres.toSet()
    }
}
