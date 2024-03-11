package pt.isel.ls.server.API

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.server.services.*
import pt.isel.ls.utils.httpError
import pt.isel.ls.utils.httpResponse
import pt.isel.ls.utils.httpStatus


class SessionsAPI(private val services:SessionServices) {
    fun getSessions(request: Request): Response {
        try {
            return httpResponse(
                services.getSessions(request.bodyString()),
                httpStatus("200")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun createSession(request: Request): Response {
        try {
            return httpResponse(
                services.createSession(
                    request.bodyString()
                ),
                httpStatus("201")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun getSession(request: Request): Response {
        try {
            return httpResponse(
                services.getSession(
                    request.path("sessionId")?.toInt()
                ),
                httpStatus("200")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }

    fun addPlayerToSession(request: Request): Response {
        try {
            return httpResponse(
                services.addPlayerToSession(
                    request.path("sessionId")?.toInt(),
                    request.bodyString()
                ),
                httpStatus("201")
            )
        } catch (e: Exception) {
            return httpError(e)
        }
    }
}
