package pt.isel.ls.utils

import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import kotlin.random.Random

internal class GameFactory{
    companion object: DataMem() {
        private val developers = listOf("Developer1", "Developer2", "Developer3")
        private val genres =
            listOf("Action", "Adventure", "RPG", "Strategy", "Puzzle", "Simulation").map { Genre(it) }.toSet()
        fun createRandomGame(): Game {
            val randomDeveloper = developers.random()
            val randomName = generateRandomString()
            val randomGenres = generateRandomGenres()
            return games.create(randomDeveloper, randomName, randomGenres)
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
}