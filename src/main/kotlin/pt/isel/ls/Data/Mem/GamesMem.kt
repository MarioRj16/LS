package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.Genre

class GamesMem(val schema: DataMemSchema): GameStorage {
    override fun create(name: String, developer: String, genres: Set<Genre>): Game {
        require(schema.gamesDB.none { it.value.name == name }){"The name of a game has to be unique"}
        require(genres.isNotEmpty()){"The game needs to have at least 1 genre in order to be created"}
        val obj = Game(id = schema.playersNextId, name = name, developer = developer, genres = genres)
        schema.gamesDB[schema.gamesNextId++] = obj
        return obj
    }

    override fun get(name: String): Game? {
        return schema.gamesDB.values.find { it.name == name }
    }

    override fun list(): List<Game> {
        return schema.gamesDB.values.toList()
    }

    override fun search(developer: String?, genres: Set<String>?): List<Game> {
        // TODO: Add Pagination and limit
        return when{
            developer.isNullOrBlank() && genres.isNullOrEmpty() -> schema.gamesDB.values.toList()
            genres.isNullOrEmpty() -> schema.gamesDB.values.filter { it.developer == developer }
            developer.isNullOrBlank() -> schema.gamesDB.values.filter { it.genres.intersect(genres).isNotEmpty() }
            else -> schema.gamesDB.values.filter {
                it.genres.intersect(genres).isNotEmpty() && it.developer == developer
            }
        }
    }
}