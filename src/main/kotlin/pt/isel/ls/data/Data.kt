package pt.isel.ls.data

interface Data {
    fun reset()

    val players: PlayersData
    val gamingSessions: GamingSessionsData
    val games: GamesData
}
