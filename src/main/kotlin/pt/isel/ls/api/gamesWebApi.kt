package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.api.models.GameResponse
import pt.isel.ls.services.GamesServices
import pt.isel.ls.utils.httpException
import pt.isel.ls.utils.json


class GamesAPI(private val services: GamesServices) {
    fun searchGames(request: Request): Response {
        try {
            /**
             * (val developer: String? = null,
             * val genres: Set<String>?  = null
             * ,val limit:Int = 30 ,
             * val skip:Int = 0)
             */

            /**
             * TODO might need to change for query
             *  val developer=request.query("developer")
             *             val genres=
             */

            return Response(Status.OK)
                .json(services.searchGames(
                    request.bodyString(),
                    request.header("Authorization"),
                    request.query("skip")?.toInt() ,
                    request.query("limit")?.toInt()
                ))
        } catch (e: Exception) {
            return httpException(e)
        }
    }

    fun createGame(request: Request): Response {
        return try {
            val gameId=services.createGame(
                request.bodyString(),
                request.header("Authorization")
            )
            Response(Status.CREATED)
                .json(GameResponse(gameId))
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun getGame(request: Request): Response {
        return try {
            Response(Status.OK)
                .json(services.getGame(
                    request.path("gameId")?.toInt(),
                    request.header("Authorization")
                ))
        } catch (e: Exception) {
            httpException(e)
        }
    }
}
