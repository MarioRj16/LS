package pt.isel.ls.data

interface Storage {
    fun reset()

    fun populate()

    val players: PlayerStorage
    val gamingSessions: GamingSessionStorage
    val games: GameStorage
}