package pt.isel.ls.data.mem

import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.paginate

class GamesMem(
    private val gamesDB: DataMemTable<Game> = DataMemTable(),
    private val genreDB: Map<Int, Genre> = setOf(
        Genre(1, "Action"),
        Genre(2, "Adventure"),
        Genre(3, "RPG"),
        Genre(4, "Simulation"),
        Genre(5, "Strategy"),
    ).associateBy { it.genreId },
) : GamesData {

    init{
        gamesDB.table[999] = Game(999, "The Witcher 3", "CD Projekt Red", setOf(genreDB[3]!!))
        gamesDB.table[1000] = Game(1000,"Cyberpunk 2077", "CD Projekt Red", setOf(genreDB[1]!!,genreDB[2]!!,genreDB[4]!!))
    }
    override fun create(
        name: String,
        developer: String,
        genres: Set<Genre>,
    ): Game {
        val game = Game(gamesDB.nextId.get(), name, developer, genres)
        gamesDB.table[gamesDB.nextId.get()] = game
        return game
    }

    override fun get(name: String): Game? = gamesDB.table.values.find { it.name == name }

    override fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): List<Game> {
        val (developer, genres) = searchParams
        val list =
            gamesDB.table.values.filter {
                (developer.isNullOrBlank() || it.developer == developer) &&
                    (genres.isEmpty() || it.genres.intersect(genres).isNotEmpty())
            }
        return list.paginate(skip, limit)
    }

    override fun genresExist(genreIds: Set<Int>): Boolean = genreIds.all { genreDB.containsKey(it) }

    override fun getGenres(genreIds: Set<Int>): Set<Genre> = genreIds.mapNotNull { genreDB[it] }.toSet()

    override fun get(id: Int): Game? = gamesDB.table.values.find { it.id == id }
}
