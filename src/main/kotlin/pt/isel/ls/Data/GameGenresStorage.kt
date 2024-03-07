package pt.isel.ls.Data

import pt.isel.ls.Domain.GameAndGenre

interface GameGenresStorage {
    fun create(gameId: Int, genre: String): GameAndGenre

    fun search(genre: String?, gameID: Int?): List<GameAndGenre>

}