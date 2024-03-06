package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.models.Game
import pt.isel.ls.models.Genre

class GamesMem(val schema: DataMemSchema): GameStorage {
    override fun create(name: String, developer: String, genres: Set<Genre>): Int {
        TODO("Not yet implemented")
    }

    override fun get(name: String): Game {
        TODO("Not yet implemented")
    }

    override fun list(): Set<Game> {
        TODO("Not yet implemented")
    }

    override fun search(developer: String, genres: Set<Genre>): Set<Game> {
        TODO("Not yet implemented")
    }
}