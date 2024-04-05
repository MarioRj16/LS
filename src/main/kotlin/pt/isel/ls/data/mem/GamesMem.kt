package pt.isel.ls.data.mem

import pt.isel.ls.api.models.GameCreate
import pt.isel.ls.api.models.GameSearch
import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.utils.paginate

class GamesMem(
    private val gamesDB: DataMemTable<Game> = DataMemTable(),
) : GamesData {
    override fun create(
        gameCreate: GameCreate,
    ): Game {
        val (name, developer, genres) = gameCreate
        require(gamesDB.table.none { it.value.name == name }) { "The name of a game has to be unique" }
        require(genres.isNotEmpty()) { "The game needs to have at least 1 genre in order to be created" }
        val game = Game(gamesDB.nextId.get(), name, developer, genres)
        gamesDB.table[gamesDB.nextId.get()] = game
        return game
    }

    override fun get(name: String): Game =
        gamesDB.table.values.find { it.name == name }
            ?: throw NoSuchElementException("No game with name $name was found")

    override fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): List<Game> {
        val (developer, genres) = searchParams
        val list =
            gamesDB.table.values.filter {
                (developer.isNullOrBlank() || it.developer == developer) &&
                    (genres.isNullOrEmpty() || it.genres.intersect(genres).isNotEmpty())
            }
        return list.paginate(skip, limit)
    }

    override fun get(id: Int): Game =
        gamesDB.table.values.find { it.id == id } ?: throw NoSuchElementException("No game with id $id was found")
}
