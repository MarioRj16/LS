package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GameGenresStorage
import pt.isel.ls.models.GameAndGenre

class GameGenreMem(val schema: DataMemSchema): GameGenresStorage {
    override fun create(gameId: Int, genre: String): GameAndGenre {
        TODO("Not yet implemented")
    }

    override fun search(genre: String, gameID: Int): Set<GameAndGenre> {
        TODO("Not yet implemented")
    }
}