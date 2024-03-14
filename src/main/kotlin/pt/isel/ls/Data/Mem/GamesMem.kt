package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.Genre
import pt.isel.ls.utils.paginate

class GamesMem(private val games: DBTableMem<Game>): GameStorage{
    override fun create(name: String, developer: String, genres: Set<Genre>): Game {
        require(games.table.none { it.value.name == name }){"The name of a game has to be unique"}
        require(genres.isNotEmpty()){"The game needs to have at least 1 genre in order to be created"}
        val obj = Game(id = games.nextId, name = name, developer = developer, genres = genres)
        games.table[games.nextId] = obj
        return obj
    }

    override fun get(name: String): Game =
        games.table.values.find { it.name == name } ?:
            throw NoSuchElementException("No game with name $name was found")

    override fun search(developer: String?, genres: Set<String>?, limit: Int, skip: Int): List<Game> {
        val list = when{
            developer.isNullOrBlank() && genres.isNullOrEmpty() -> games.table.values.toList()
            genres.isNullOrEmpty() -> games.table.values.filter { it.developer == developer }
            developer.isNullOrBlank() -> games.table.values.filter { it.genres.intersect(genres).isNotEmpty() }
            else -> games.table.values.filter {
                it.genres.intersect(genres).isNotEmpty() && it.developer == developer
            }
        }

        return list.paginate(skip, limit)
    }
}