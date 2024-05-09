package pt.isel.ls.utils.factories

import pt.isel.ls.data.GenresData
import pt.isel.ls.domain.Genre
import kotlin.random.Random

class GenresFactory(private val genresDB: GenresData) {
    /**
     * Not really a factory as per the definition of the factory pattern, since all genres are added to the DB
     * once the server starts running.
     */
    fun random(): Set<Genre> {
        val genres = genresDB.getAllGenres()
        require(genres.isNotEmpty()) { "No genres in the database" }
        val listSize = Random.nextInt(1, genres.size + 1)
        return List(listSize) { genres.random() }.toSet()
    }
}
