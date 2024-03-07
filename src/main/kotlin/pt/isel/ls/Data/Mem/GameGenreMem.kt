package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameGenresStorage
import pt.isel.ls.Domain.GameAndGenre

class GameGenreMem(val schema: DataMemSchema): GameGenresStorage {
    override fun create(gameId: Int, genre: String): GameAndGenre {
        val obj = GameAndGenre(game = gameId, genre = genre)
        schema.gamesGenresDB += obj
        return obj
    }

    override fun search(genre: String?, gameID: Int?): List<GameAndGenre> {
        // TODO: Write better exception messages
        // TODO: Find out if this is the best way of doing searches
        if(genre.isNullOrBlank() && gameID == null)
            throw IllegalArgumentException("The arguments can't all be null")
        return schema.gamesGenresDB.filter { it.genre == genre || it.game == gameID }
    }
}