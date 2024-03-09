package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.services.serviceCreateGame
import pt.isel.ls.server.services.serviceGetGame
import pt.isel.ls.server.services.serviceSearchGames
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus


fun searchGames(request: Request):Response{
    try{
        return httpResponse(
            serviceSearchGames(request.bodyString()),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}
fun createGame(request: Request):Response{
    try{
        return httpResponse(
            serviceCreateGame(request.bodyString()),
            httpStatus("201")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}

fun getGame(request: Request):Response{
    try{
        return httpResponse(
            serviceGetGame(request.path("gameId")?.toInt()),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}
