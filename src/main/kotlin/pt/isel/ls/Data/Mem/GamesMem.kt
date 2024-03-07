package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.Genre

class GamesMem(val schema: DataMemSchema): GameStorage {
    override fun create(name: String, developer: String, genres: Set<Genre>): Int {
        schema.gamesDB[schema.gamesNextId] = Game(id = schema.playersNextId, name = name, developer = developer)
        return schema.gamesNextId++
    }

    override fun get(name: String): Game? {
        return schema.gamesDB.values.find { it.name == name }
    }

    override fun list(): List<Game> {
        return schema.gamesDB.values.toList()
    }

    override fun search(developer: String, genres: Set<Genre>): List<Game> {
        TODO("Not yet implemented")
    }
}