package pt.isel.ls.Data.Postgres

import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Domain.Game
import pt.isel.ls.Domain.Genre
import pt.isel.ls.utils.exceptions.ObjectDoesNotExistException
import pt.isel.ls.utils.getSublistLastIdx
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class GamesPostgres(private val conn: Connection): GameStorage {

    override fun create(name: String, developer: String, genres: Set<Genre>): Game = conn.use {
        val game: Game

        var stm = it.prepareStatement(
            """insert into games(game_name, developer) values (?, ?)""".trimIndent(),
            Statement.RETURN_GENERATED_KEYS
        )
        stm.setString(1, name)
        stm.setString(2, developer)

        if(stm.executeUpdate() == 0)
            throw SQLException("Creating game failed, no rows affected")

        val generatedKeys = stm.generatedKeys

        if(generatedKeys.next())
            game = get(name)!!
        else
            throw SQLException("Creating game failed, no ID was created")

        // TODO: extract without causing any conflict
        for(genre in genres){
            stm = it.prepareStatement(
                """insert into games_genres(game, genre) values (?, ?)""",
                Statement.RETURN_GENERATED_KEYS
            )
            stm.setInt(1, game.id)
            stm.setString(2, genre.genre)

            if(stm.executeUpdate() == 0 || !stm.generatedKeys.next())
                throw SQLException("Creating game and genre association failed, no rows affected")
        }


        return game
    }

    override fun get(name: String): Game? = conn.use {
        val gameStm = it.prepareStatement("""select * from games where game_name = ?""".trimIndent())

        gameStm.setString(1, name)
        val gameRS = gameStm.executeQuery()

        if(gameRS.next()){
            val gameId = gameRS.getInt(1)
            val gameName = gameRS.getString(2)
            val gameDeveloper = gameRS.getString(3)

            val genreStm = it.prepareStatement("""select * from games_genres where game = ?""".trimIndent())

            genreStm.setInt(1, gameId)
            val genreRS = genreStm.executeQuery()
            val genres = mutableSetOf<Genre>()

            while(genreRS.next()){
                genres.add(Genre(genreRS.getString(1)))
            }


            return Game(
                id = gameId,
                name = gameName,
                developer = gameDeveloper,
                genres = genres.toSet()
            )
        }

        throw ObjectDoesNotExistException("Could not get Game, $name was not found")
    }


    override fun list(limit: Int, skip: Int): List<Game> = conn.use {
        val games = mutableSetOf<Game>()

        val gameStm = it.prepareStatement("""select * from games""")

        val gameRS = gameStm.executeQuery()
        while(gameRS.next()){
            val gameId = gameRS.getInt(1)
            val gameName = gameRS.getString(2)
            val gameDeveloper = gameRS.getString(3)

            val genreStm = it.prepareStatement("""select * from games_genres where game = ?""")
            genreStm.setInt(1, gameId)
            val genreRS = genreStm.executeQuery()
            val genres = mutableSetOf<Genre>()

            while(genreRS.next()){
                genres.add(Genre(genreRS.getString(1)))
            }

            games.add(Game(id = gameId, name = gameName, developer = gameDeveloper, genres = genres.toSet()))
        }



        return games.toList().subList(skip, games.getSublistLastIdx(skip, limit))
    }

    override fun search(developer: String?, genres: Set<String>?, limit: Int, skip: Int): List<Game> = conn.use {
        val games = mutableSetOf<Game>()
        var query = """select * from games ${genres?.joinToString(separator = ", ", prefix = "(", postfix = ")")}"""
        if(developer is String)
            query += """ and developer = $developer"""

        val gameStm = it.prepareStatement(query)

        val gameRS = gameStm.executeQuery()
        while (gameRS.next()){
            val gameId = gameRS.getInt(1)
            val gameName = gameRS.getString(2)
            val gameDeveloper = gameRS.getString(3)

            val genreStm = it.prepareStatement("""select * from games_genres where game = ?""")
            genreStm.setInt(1, gameId)
            val genreRS = genreStm.executeQuery()
            val foundGenres = mutableSetOf<Genre>()

            while(genreRS.next()){
                foundGenres.add(Genre(genreRS.getString(1)))
            }

            games.add(Game(id = gameId, name = gameName, developer = gameDeveloper, genres = foundGenres.toSet()))
        }
        return games.toList().subList(skip, games.getSublistLastIdx(skip, limit))
    }
}