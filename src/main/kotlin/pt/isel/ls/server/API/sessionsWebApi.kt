package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.services.*
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus

fun getSessions(request: Request):Response{
    try{
        return httpResponse(
            serviceGetSessions(request.bodyString()),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}

fun createSession(request: Request):Response{
    try{
        return httpResponse(
            serviceCreateSession(request.bodyString()),
            httpStatus("201")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}

fun getSession(request: Request):Response{
    try{
        return httpResponse(
            serviceGetSession(request.path("sessionId")?.toInt()),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}

fun addPlayertoSession(request: Request):Response{
    try{
        return httpResponse(
            serviceAddPlayertoSession(request.path("sessionId")?.toInt(),request.bodyString()),
            httpStatus("200")
        )
    }catch (e:Exception){
        return httpError(e)
    }
}
