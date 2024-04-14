package pt.isel.ls.utils.factories

import pt.isel.ls.domain.Genre
import kotlin.random.Random

class GenresFactory(
    private val genresDB: Map<Int, Genre> = setOf(
        Genre(1, "Action"),
        Genre(2, "Adventure"),
        Genre(3, "RPG"),
        Genre(4, "Simulation"),
        Genre(5, "Strategy"),
    ).associateBy { it.genreId },
) {
    fun generateRandomGenres(): Set<Genre> {
        return List(Random.nextInt(1, genresDB.size + 1)) { genresDB.values.random() }.toSet()
    }
}