package pt.isel.ls.data.mem

import pt.isel.ls.data.GenresData
import pt.isel.ls.domain.Genre

class GenresMem(
    private val genreDB: DataMemTable<Genre> = DataMemTable()
): GenresData {
    init {
        setOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "RPG"),
            Genre(4, "Simulation"),
            Genre(5, "Strategy"),
        ).forEach{ genreDB.table[genreDB.nextId] = it }
    }

    override fun getGenres(genreIds: Set<Int>): Set<Genre> =
        genreDB.table.values.filter { genre -> genreIds.contains(genre.genreId) }.toSet()

    override fun getAllGenres(): Set<Genre> = genreDB.table.values.toSet()

    override fun genresExist(genreIds: Set<Int>): Boolean = genreIds.all { genreDB.table.containsKey(it) }

}