package pt.isel.ls.Data

interface Storage {
    val players: PlayerStorage
    val gamingSessions: GamingSessionStorage
    val games: GameStorage
}