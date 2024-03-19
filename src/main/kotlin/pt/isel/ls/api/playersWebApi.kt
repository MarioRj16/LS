package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.api.models.PlayerResponse
import pt.isel.ls.services.PlayerServices
import pt.isel.ls.utils.*

class PlayersAPI(private val services: PlayerServices) {
    fun createPlayer(request: Request): Response {
        return try {
            val player=services.createPlayer(request.bodyString())
            Response(Status.CREATED)
                .json(PlayerResponse(player.token,player.id))
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun getPlayer(request: Request): Response {
        return try {

            Response(Status.OK)
                .json(services.getPlayer(
                    request.path("playerId")?.toInt(),
                    request.header("Authorization")
                ),
                )
        } catch (e: Exception) {
            httpException(e)
        }
    }
}