package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.services.serviceCreatePlayer
import pt.isel.ls.server.services.serviceGetPlayer
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus


fun createPlayer(request: Request):Response{
    try {
        //TODO could IllegalArgumentException if there isn't the required parameters and so we could
        // add it to the httpResponse utils
        return httpResponse(
            serviceCreatePlayer(request.bodyString()),
            httpStatus("201")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}

fun getPlayer(request: Request):Response{
    try{
        return httpResponse(
            serviceGetPlayer(request.path("playerId")?.toInt()),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}
