package pt.isel.ls.data.mem

import pt.isel.ls.data.Data
import pt.isel.ls.data.GamesData
import pt.isel.ls.data.PlayersData
import pt.isel.ls.data.SessionsData

open class DataMem : Data, DataMemSchema() {

    override fun reset() {
        playersDB.table.clear()
        sessionsDB.table.clear()
        gamesDB.table.clear()
    }

    override val players: PlayersData = PlayersMem(playersDB)

    override val gamingSessions: SessionsData = SessionsMem(sessionsDB, gamesDB, playersDB)

    override val games: GamesData = GamesMem(gamesDB)

    override val genres: GenresMem = GenresMem(genreDB)
}
