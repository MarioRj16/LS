package pt.isel.ls.api

import pt.isel.ls.services.Services

open class API(services: Services) : APISchema() {
    val playerAPI = PlayersAPI(services.playersServices)
    val gamesAPI = GamesAPI(services.gamesServices)
    val sessionsAPI = SessionsAPI(services.gamingSessionsServices)
}
