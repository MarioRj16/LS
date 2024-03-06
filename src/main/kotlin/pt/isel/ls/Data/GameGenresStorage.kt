package pt.isel.ls.Data

import pt.isel.ls.models.GameAndGenre

interface GameGenresStorage {
    fun create(gameId: Int, genre: String): GameAndGenre

    fun search(genre: String, gameID: Int): Set<GameAndGenre>

}