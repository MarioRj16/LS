package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.server.services.*
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpException
import pt.isel.ls.utils.httpStatus
import pt.isel.ls.utils.json


class SessionsAPI(private val services:SessionServices) {
    fun searchSessions(request: Request): Response {
        try {
            return Response(Status.OK)
                .json(services.searchSessions(request.bodyString()))
        } catch (e: Exception) {
            return httpException(e)
        }
    }

    fun createSession(request: Request): Response {
        try {
            return Response(Status.CREATED)
                .json(services.createSession(request.bodyString()))
        } catch (e: Exception) {
            return httpException(e)
        }
    }

    fun getSession(request: Request): Response {
        try {
            return Response(Status.OK)
                .json(services.getSession(request.path("sessionId")?.toInt()))
        } catch (e: Exception) {
            return httpException(e)
        }
    }

    fun addPlayerToSession(request: Request): Response {
        try {
            return Response(Status.OK)
                .json(services.addPlayerToSession(
                    request.path("sessionId")?.toInt(),
                    request.bodyString()))
        } catch (e: Exception) {
            return httpException(e)
        }
    }
}
