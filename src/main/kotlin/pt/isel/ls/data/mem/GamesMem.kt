package pt.isel.ls.data.mem

import pt.isel.ls.data.GameStorage
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.paginate

class GamesMem(private val games: DBTableMem<Game>): GameStorage{
    override fun create(name: String, developer: String, genres: Set<Genre>): Game {
        println(games.table.filter { it.value.name == name })
        require(games.table.none { it.value.name == name }){"The name of a game has to be unique"}
        require(genres.isNotEmpty()){"The game needs to have at least 1 genre in order to be created"}
        val game=Game(id = games.nextId, name = name, developer = developer, genres = genres)
        games.table[games.nextId] = game
        return games.table[game.id] ?: throw NoSuchElementException("The game could not be created")
    }

    override fun get(name: String): Game =
        games.table.values.find { it.name == name } ?:
            throw NoSuchElementException("No game with name $name was found")

    override fun search(developer: String?, genres: Set<String>?, limit: Int, skip: Int): List<Game> {
        val list = games.table.values.filter {
            (developer.isNullOrBlank() || it.developer == developer) &&
            (genres.isNullOrEmpty() || it.genres.intersect(genres).isNotEmpty())
        }
        return list.paginate(skip, limit)
    }

    override fun getById(id: Int): Game {
        return games.table.values.find { it.id == id } ?:
        throw NoSuchElementException("No game with id $id was found")
    }
}