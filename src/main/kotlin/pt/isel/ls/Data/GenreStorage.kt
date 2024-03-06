package pt.isel.ls.Data


interface GenreStorage {
    fun create(genre: String): String

    fun exists(genre: String): Boolean
}