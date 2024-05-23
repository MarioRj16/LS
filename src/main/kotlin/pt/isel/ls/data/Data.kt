package pt.isel.ls.data

interface Data {
    fun reset()

    val players: PlayersData
    val gamingSessions: SessionsData
    val games: GamesData
    val genres: GenresData
}
