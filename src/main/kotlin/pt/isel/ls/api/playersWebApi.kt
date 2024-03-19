package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.api.models.PlayerResponse
import pt.isel.ls.domain.Player
import pt.isel.ls.services.PlayerServices
import pt.isel.ls.utils.*
import pt.isel.ls.utils.postgres.useWithRollback
import java.util.*

class PlayersAPI(private val services: PlayerServices) {
    fun createPlayer(request: Request): Response {
        try {
            val player=services.createPlayer(request.bodyString())
            return Response(Status.CREATED)
                .json(PlayerResponse(player.token,player.id))
        } catch (e: Exception) {
            return httpException(e)
        }
    }

    fun getPlayer(request: Request): Response {
        try {

            return Response(Status.OK)
                .json(services.getPlayer(
                    request.path("playerId")?.toInt(),
                    request.header("Authorization")
                ),
            )
        } catch (e: Exception) {
            return httpException(e)
        }
    }
}