package pt.isel.ls.data.postgres

import pt.isel.ls.api.models.games.GameCreate
import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.paginate
import pt.isel.ls.utils.postgres.toGame
import pt.isel.ls.utils.postgres.toGenre
import pt.isel.ls.utils.postgres.useWithRollback
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement

class GamesPostgres(private val conn: () -> Connection) : GamesData {
    override fun create(
        gameCreate: GameCreate,
    ): Game =
        conn().useWithRollback {
            val game: Game
            val (name, developer, genres) = gameCreate
            val gameId = it.insertGame(name, developer)

            game = Game(gameId, name, developer, genres)

            if (genres.isNotEmpty()) {
                it.insertGamesGenres(gameId, genres)
            }

            return game
        }

    override fun get(name: String): Game = fetchGame("game_name", name)

    override fun get(id: Int): Game = fetchGame("game_id", "$id")
    override fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): List<Game> =
        conn().useWithRollback {
            val query = buildSearchQuery(searchParams)
            val statement = it.prepareStatement(query)
            setStatementParameters(statement, searchParams)

            val resultSet = statement.executeQuery()
            val games = mutableListOf<Game>()
            val foundGenres = mutableSetOf<Genre>()
            var previousGameId: Int? = null

            while (resultSet.next()) {
                val currentGameId = resultSet.getInt("game_id")
                if (currentGameId != previousGameId && previousGameId != null) {
                    games += resultSet.toGame(foundGenres.toSet(), previousGameId)
                    foundGenres.clear()
                }
                foundGenres += resultSet.toGenre()
                previousGameId = currentGameId
            }

            if (previousGameId != null) {
                games += resultSet.toGame(foundGenres.toSet(), previousGameId)
            }

            return games.paginate(skip, limit)
        }

    private fun Connection.insertGame(name: String, developer: String): Int {
        val query = """insert into games(game_name, developer) values (?, ?)""".trimIndent()
        val statement =
            prepareStatement(query, Statement.RETURN_GENERATED_KEYS).apply {
                setString(1, name)
                setString(2, developer)
            }

        if (statement.executeUpdate() == 0) {
            throw SQLException("Creating game failed, no rows affected")
        }

        val generatedKeys = statement.generatedKeys

        if (!generatedKeys.next()) {
            throw SQLException("Creating game failed, no ID was created")
        }

        return generatedKeys.getInt(1)
    }

    private fun Connection.insertGamesGenres(gameId: Int, genres: Set<Genre>) {
        val query =
            """
            INSERT INTO games_genres(game, genre) VALUES ${genres.joinToString(", ") { "(?, ?)" }}
            """.trimIndent()

        val statement =
            prepareStatement(query, Statement.RETURN_GENERATED_KEYS).apply {
                var counter = 1
                for (genre in genres) {
                    setInt(counter++, gameId)
                    setInt(counter++, genre.genreId)
                }
            }

        if (statement.executeUpdate() == 0 || !statement.generatedKeys.next()) {
            throw SQLException("Creating game and genre association failed, no rows affected")
        }
    }

    private fun fetchGame(identifier: String, value: String): Game =
        conn().useWithRollback {
            val query =
                """
                SELECT * FROM games 
                INNER JOIN games_genres ON games.game_id = games_genres.game
                WHERE $identifier = ?
                """.trimIndent()

            val statement = it.prepareStatement(query).apply {
                setString(1, value)
            }

            val resultSet = statement.executeQuery()
            val genres = mutableSetOf<Genre>()

            while (resultSet.next())
                genres += resultSet.toGenre()

            if (genres.isNotEmpty()) {
                return resultSet.toGame(genres.toSet())
            }

            throw NoSuchElementException("Could not get Game, $value was not found")
        }

    private fun buildSearchQuery(searchParams: GameSearch): String {
        val (developer, genres) = searchParams

        val genreParams = genres?.joinToString(", ") { "?" } ?: ""
        val genreCondition = if (genres.isNullOrEmpty()) "" else " AND genre IN ($genreParams)"
        val developerCondition = if (developer.isNullOrEmpty()) "" else " AND developer = ?"

        return (
            """
            SELECT * FROM games 
            FULL OUTER JOIN games_genres ON games.game_id = games_genres.game 
            WHERE 1 = 1 $genreCondition $developerCondition
            """.trimIndent()
            )
    }

    private fun setStatementParameters(statement: PreparedStatement, searchParams: GameSearch) {
        val (developer, genres) = searchParams
        var parameterIdx = 1

        genres?.forEach { genre -> statement.setString(parameterIdx++, genre.name) }
        developer?.let { statement.setString(parameterIdx, developer) }
    }
}
