package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.Domain.PlayerResponse
import pt.isel.ls.server.services.PlayerServices
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpPlayerResponse
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus

class PlayersAPI(private val services: PlayerServices) {
    fun createPlayer(request: Request): Response {
        try {
            //TODO could IllegalArgumentException if there isn't the required parameters and so we could
            // add it to the httpResponse utils
            val player=services.createPlayer(request.bodyString())
            /*return httpResponse(
                PlayerResponse(player.token,player.id),
                httpStatus("201")
            )

             */
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun getPlayer(request: Request): Response {
        try {
            return httpResponse(
                services.getPlayer(request.path("playerId")?.toInt()),
                httpStatus("200")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }
}