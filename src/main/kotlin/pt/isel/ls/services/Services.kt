package pt.isel.ls.services

import pt.isel.ls.data.Data

open class Services(val data: Data) {
    val playersServices = PlayersServices(data)
    val gamesServices = GamesServices(data)
    val gamingSessionsServices = SessionsServices(data)
    val genresServices = GenresServices(data)
}
