package pt.isel.ls.data.mem

import pt.isel.ls.data.Data
import pt.isel.ls.data.GamesData
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.data.PlayersData

open class DataMem : Data, DataMemSchema() {

    override fun reset() {
        playersDB.table.clear()
        sessionsDB.table.clear()
        gamesDB.table.clear()
    }

    override val players: PlayersData = PlayersMem(playersDB)

    override val gamingSessions: GamingSessionsData = GamingSessionsMem(sessionsDB, gamesDB, playersDB)

    override val games: GamesData = GamesMem(gamesDB)

    override val genres: GenresMem = GenresMem(genreDB)
}
