package pt.isel.ls.data

import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre

interface GamesData {
    fun create(name: String, developer: String, genres: Set<Genre>): Game

    fun get(name: String): Game

    fun search(developer: String?, genres: Set<Genre>?, limit: Int, skip: Int): List<Game>

    fun getById(id: Int): Game
}