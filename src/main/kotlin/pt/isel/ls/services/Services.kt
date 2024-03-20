package pt.isel.ls.services

import pt.isel.ls.data.Storage

open class Services(data: Storage) {
    val playersServices = PlayerServices(data)
    val gamesServices = GamesServices(data)
    val gamingSessionsServices = SessionServices(data)
}