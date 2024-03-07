package pt.isel.ls.server.API

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.logRequest
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus


fun createPlayer(request: Request):Response{
    try {
        return httpResponse(
            serviceCreatePlayer(Json.decodeFromString<PlayerInput>(request.bodyString())),
            httpStatus("201")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}

fun getPlayer(request: Request):Response{
    try{
        return httpResponse(
            serviceGetPlayer(request.path("playerId")),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}
