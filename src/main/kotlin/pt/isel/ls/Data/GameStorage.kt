package pt.isel.ls.Data

import pt.isel.ls.models.Game
import pt.isel.ls.models.Genre

interface GameStorage {
    fun create(name: String, developer: String, genres: Set<Genre>): Int

    fun get(name: String): Game

    fun list(): Set<Game>

    fun search(developer: String, genres: Set<Genre>): Set<Game>
}