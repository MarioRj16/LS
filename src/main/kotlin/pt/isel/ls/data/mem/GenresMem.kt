package pt.isel.ls.data.mem

import pt.isel.ls.data.GenresData
import pt.isel.ls.domain.Genre

class GenresMem(
    private val genreDB: Map<Int, Genre> = setOf(
        Genre(1, "Action"),
        Genre(2, "Adventure"),
        Genre(3, "RPG"),
        Genre(4, "Simulation"),
        Genre(5, "Strategy"),
    ).associateBy { it.genreId }
): GenresData {

    override fun getGenres(genreIds: Set<Int>): Set<Genre> = genreIds.mapNotNull { genreDB[it] }.toSet()

    override fun getAllGenres(): Set<Genre> = genreDB.values.toSet()

    override fun genresExist(genreIds: Set<Int>): Boolean = genreIds.all { genreDB.containsKey(it) }

}