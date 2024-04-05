package pt.isel.ls.services

import pt.isel.ls.data.Data

open class Services(val data: Data) : ServicesSchema() {
    val playersServices = PlayerServices(data)
    val gamesServices = GamesServices(data)
    val gamingSessionsServices = SessionServices(data)
}
