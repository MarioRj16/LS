package pt.isel.ls.Data

interface Storage {

    val players: PlayerStorage
    val sessions: SessionStorage
    val games: GameStorage
    val gameGenres: GameGenresStorage
    val genres: GenreStorage
    val playerSessions: PlayerSessionsStorage
}