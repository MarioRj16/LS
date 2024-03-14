package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.services.*
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpStatus
import pt.isel.ls.utils.json


class SessionsAPI(private val services:SessionServices) {
    fun searchSessions(request: Request): Response {
        try {
            return Response(httpStatus("200"))
                .json(services.searchSessions(request.bodyString()))
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun createSession(request: Request): Response {
        try {
            return Response(httpStatus("201"))
                .json(services.createSession(request.bodyString()))
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun getSession(request: Request): Response {
        try {
            return Response(httpStatus("200"))
                .json(services.getSession(request.path("sessionId")?.toInt()))
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun addPlayerToSession(request: Request): Response {
        try {
            return Response(httpStatus("201"))
                .json(services.addPlayerToSession(
                    request.path("sessionId")?.toInt(),
                    request.bodyString()))
        } catch (e: Exception) {
            return httpError(e)
        }
    }
}
