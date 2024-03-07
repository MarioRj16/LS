package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.*

class DataMem(val schema: DataMemSchema): Storage {
    override val players: PlayerStorage
        get() = TODO("Not yet implemented")
    override val sessions: SessionStorage
        get() = TODO("Not yet implemented")
    override val games: GameStorage
        get() = TODO("Not yet implemented")
    override val gameGenres: GameGenresStorage
        get() = TODO("Not yet implemented")
    override val genres: GenreStorage
        get() = TODO("Not yet implemented")
    override val playerSessions: PlayerSessionsStorage
        get() = TODO("Not yet implemented")

}