package pt.isel.ls.Data

import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.Genre

interface GameStorage {
    fun create(name: String, developer: String, genres: Set<Genre>): Game?

    fun get(name: String): Game?

    fun getByID(id:Int): Game?

    fun list(): List<Game>

    fun search(developer: String?, genres: Set<String>?): List<Game>
}