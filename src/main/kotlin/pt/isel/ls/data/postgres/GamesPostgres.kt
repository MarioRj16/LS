package pt.isel.ls.data.postgres

import pt.isel.ls.api.models.games.GameSearch
import pt.isel.ls.data.GamesData
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.postgres.toGame
import pt.isel.ls.utils.postgres.toGenre
import pt.isel.ls.utils.postgres.useWithRollback
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import pt.isel.ls.utils.PaginateResponse

class GamesPostgres(private val conn: () -> Connection) : GamesData {
    override fun create(
        name: String,
        developer: String,
        genres: Set<Genre>,
    ): Game =
        conn().useWithRollback {
            val game: Game
            val gameId = it.insertGame(name, developer)

            game = Game(gameId, name, developer, genres)

            if (genres.isNotEmpty()) {
                it.insertGamesGenres(gameId, genres)
            }

            return game
        }

    override fun get(name: String): Game? = fetchGame("game_name", name)

    override fun get(id: Int): Game? = fetchGame("game_id", id)

    override fun search(
        searchParams: GameSearch,
        limit: Int,
        skip: Int,
    ): PaginateResponse<Game> =
        conn().useWithRollback { it ->
            val query = buildSearchQuery(searchParams)
            val statement = it.prepareStatement(query)
            setStatementParameters(statement, searchParams)

            val resultSet = statement.executeQuery()
            val games = mutableListOf<Game>()

            while (resultSet.next()) {
                val currentGameId = resultSet.getInt("game_id")
                games += resultSet.toGame(setOf(resultSet.toGenre()), currentGameId)
            }

            val list = games.groupBy { it.id }.map { (_, gameList) ->
                val game = gameList.first()
                game.copy(genres = gameList.flatMap { it.genres }.toSet())
            }

            return PaginateResponse.fromList(list, skip, limit)
        }

    private fun Connection.insertGame(name: String, developer: String): Int {
        val query = """insert into games(game_name, developer) values (?, ?)"""
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
        val query = """INSERT INTO games_genres(game_id, genre_id) VALUES ${genres.joinToString(", ") { "(?, ?)" }}"""

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

    private fun fetchGame(identifier: String, value: Any): Game? =
        conn().useWithRollback {
            val query =
                """
                SELECT * FROM games 
                INNER JOIN games_genres USING (game_id)
                INNER JOIN genres USING (genre_id)
                WHERE $identifier = ?
                """.trimIndent()

            val statement = it.prepareStatement(query).apply {
                setObject(1, value)
            }

            val resultSet = statement.executeQuery()
            val genres = mutableSetOf<Genre>()

            while (resultSet.next()) {
                genres += resultSet.toGenre()
                if (resultSet.isLast && genres.isNotEmpty()) {
                    return resultSet.toGame(genres.toSet())
                }
            }
            return null
        }

    private fun buildSearchQuery(searchParams: GameSearch): String {
        val (name, developer, genres) = searchParams
        val genreParams = genres.joinToString(", ") { "?" }
        val genreCondition = if (genres.isEmpty()) "" else " AND genre_id IN ($genreParams)"
        val developerCondition = if (developer.isNullOrEmpty()) "" else " AND developer = ?"
        val nameCondition = if (name.isNullOrEmpty()) "" else " AND game_name ILIKE ?"

        return (
            """
            SELECT * FROM games 
            INNER JOIN games_genres USING (game_id) 
            INNER JOIN genres USING (genre_id)
            WHERE 1 = 1 $genreCondition $developerCondition $nameCondition
            """.trimIndent()
            )
    }

    private fun setStatementParameters(statement: PreparedStatement, searchParams: GameSearch) {
        val (name, developer, genres) = searchParams
        var parameterIdx = 1

        genres.forEach { genre -> statement.setInt(parameterIdx++, genre) }
        developer?.let { statement.setString(parameterIdx++, developer) }
        name?.let { statement.setString(parameterIdx, "$name%") }
    }
}
