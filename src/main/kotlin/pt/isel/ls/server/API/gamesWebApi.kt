package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.services.GamesServices
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus


class GamesAPI(private val services: GamesServices) {
    fun searchGames(request: Request): Response {
        try {
            return httpResponse(
                services.searchGames(request.bodyString()),
                httpStatus("200")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun createGame(request: Request): Response {
        try {
            return httpResponse(
                services.createGame(request.bodyString()),
                httpStatus("201")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun getGame(request: Request): Response {
        try {
            return httpResponse(
                services.getGame(request.path("gameId")?.toInt()),
                httpStatus("200")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }
}
