package pt.isel.ls.data

import pt.isel.ls.domain.Genre

interface GenresData {

    fun getGenres(genreIds: Set<Int>): Set<Genre>

    fun getAllGenres(): Set<Genre>

    fun genresExist(genreIds: Set<Int>): Boolean
}