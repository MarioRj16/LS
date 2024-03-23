package pt.isel.ls.data.postgres

import pt.isel.ls.data.GameStorage
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.paginate
import pt.isel.ls.utils.postgres.toGame
import pt.isel.ls.utils.postgres.toGenre
import pt.isel.ls.utils.postgres.useWithRollback
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class GamesPostgres(private val conn:  ()-> Connection): GameStorage {

    override fun create(name: String, developer: String, genres: Set<Genre>): Game = conn().useWithRollback {
        val game: Game

        var statement = it.prepareStatement(
            """insert into games(game_name, developer) values (?, ?)""".trimIndent(),
            Statement.RETURN_GENERATED_KEYS
        ).apply {
            setString(1, name)
            setString(2, developer)
        }

        if (statement.executeUpdate() == 0)
            throw SQLException("Creating game failed, no rows affected")

        val generatedKeys = statement.generatedKeys

        if (generatedKeys.next())
            game = get(name)
        else
            throw SQLException("Creating game failed, no ID was created")

        // TODO: extract without causing any conflict
        if (genres.isNotEmpty()) {
            var query = """insert into games_genres(game, genre) values """

            for (idx in genres.indices) {
                query += "(?, ?)"
                if (idx + 1 < genres.size)
                    query += ", "
            }

            statement = it.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).apply {
                var counter = 1
                for (genre in genres) {
                    setInt(counter++, game.id)
                    setString(counter++, genre.genre)
                }
            }

            if (statement.executeUpdate() == 0 || !statement.generatedKeys.next())
                throw SQLException("Creating game and genre association failed, no rows affected")
        }
        return game
    }

    override fun get(name: String): Game = conn().useWithRollback {
        val statement = it.prepareStatement(
            """
            select * from games 
            inner join games_genres
            on games.game_id = games_genres.game
            where game_name = ?
            """.trimIndent()
        ).apply {
            setString(1, name)
        }

        val resultSet = statement.executeQuery()
        val genres = mutableSetOf<Genre>()

        while (resultSet.next()) {
            genres += resultSet.toGenre()
            if (resultSet.isLast)
                return resultSet.toGame(genres.toSet())
        }
        throw NoSuchElementException("Could not get Game, $name was not found")
    }
    override fun getById(id:Int): Game = conn().useWithRollback {
        val statement = it.prepareStatement(
            """
            select * from games 
            inner join games_genres
            on games.game_id = games_genres.game
            where game_id = ?
            """.trimIndent()
        ).apply {
            setInt(1, id)
        }

        val resultSet = statement.executeQuery()
        val genres = mutableSetOf<Genre>()

        while (resultSet.next()) {
            genres += resultSet.toGenre()
            if (resultSet.isLast)
                return resultSet.toGame(genres.toSet())
        }
        throw NoSuchElementException("Could not get Game, $id was not found")
    }

    override fun search(developer: String?, genres: Set<Genre>?, limit: Int, skip: Int): List<Game> = conn().useWithRollback {
        val query =
            """
            select * from games 
            inner join games_genres
            on games.game_id = games_genres.game where
            ${genres?.joinToString(separator = "?, ", prefix = "genre in (", postfix = ")")}
            ${developer?.let { """ and developer = ?""" }}
            """.trimIndent()


            val statement = it.prepareStatement(query).apply {
                var parameterIdx = 1
                genres?.forEach { genre -> setString(parameterIdx++, genre.genre) }
                developer?.let { setString(parameterIdx, developer) }
            }

            val resultSet = statement.executeQuery()
            val games = mutableListOf<Game>()
            val foundGenres = mutableSetOf<Genre>()
            var previousGameId: Int? = null
            while (resultSet.next()) {
                foundGenres += resultSet.toGenre()
                val currentGameId = resultSet.getInt("game_id")
                if (previousGameId == currentGameId) {
                    games += resultSet.toGame(foundGenres.toSet())
                    foundGenres.clear()
                    continue
                }
                previousGameId = currentGameId
            }
            return games.paginate(skip, limit)
        }
}