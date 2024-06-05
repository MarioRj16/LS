package pt.isel.ls.data.postgres

import pt.isel.ls.data.GenresData
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.postgres.toGenre
import pt.isel.ls.utils.postgres.useWithRollback
import java.sql.Connection

class GenresPostgres(private val conn: () -> Connection) : GenresData {

    override fun getGenres(genreIds: Set<Int>): Set<Genre> = conn().useWithRollback {
        val query = """SELECT * FROM genres WHERE genre_id IN (${genreIds.joinToString(", ")})"""
        val statement = it.prepareStatement(query)
        val resultSet = statement.executeQuery()
        val genres = mutableSetOf<Genre>()

        while (resultSet.next()) {
            genres += resultSet.toGenre()
        }

        return genres
    }

    override fun getAllGenres(): Set<Genre> {
        conn().useWithRollback {
            val query = """SELECT * FROM genres"""
            val statement = it.prepareStatement(query)

            val resultSet = statement.executeQuery()
            val set = mutableSetOf<Genre>()
            while (resultSet.next()) {
                set.add(resultSet.toGenre())
            }
            return set
        }
    }

    override fun genresExist(genreIds: Set<Int>): Boolean = conn().useWithRollback {
        val query = """SELECT COUNT(*) FROM genres WHERE genre_id IN (${genreIds.joinToString(", ")})"""
        val statement = it.prepareStatement(query)
        val resultSet = statement.executeQuery()
        resultSet.next()
        resultSet.getInt(1) == genreIds.size
    }
}
