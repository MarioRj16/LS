package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.Domain.GameResponse
import pt.isel.ls.server.services.GamesServices
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpStatus
import pt.isel.ls.utils.json


class GamesAPI(private val services: GamesServices) {
    fun searchGames(request: Request): Response {
        try {
            return Response( httpStatus("200"))
                .json(services.searchGames(request.bodyString()))
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun createGame(request: Request): Response {
        try {
            val gameId=services.createGame(request.bodyString())
            require(gameId!=null)
            return Response(httpStatus("201"))
                .json(GameResponse(gameId))
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun getGame(request: Request): Response {
        try {
            return Response(httpStatus("200"))
                .json(services.getGame(request.path("gameId")?.toInt()))
        } catch (e: Exception) {
            return httpError(e)
        }
    }
}
