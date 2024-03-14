package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.Domain.PlayerResponse
import pt.isel.ls.server.services.PlayerServices
import pt.isel.ls.utils.*

class PlayersAPI(private val services: PlayerServices) {
    fun createPlayer(request: Request): Response {
        try {
            val player=services.createPlayer(request.bodyString())
            return Response(httpStatus("201"))
                .json(PlayerResponse(player.token,player.id))
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun getPlayer(request: Request): Response {
        try {
            return Response(httpStatus("200"))
                .json(services.getPlayer(request.path("playerId")?.toInt()),
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }
}