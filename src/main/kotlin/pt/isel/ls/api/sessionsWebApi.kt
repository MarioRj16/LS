package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.services.SessionServices
import pt.isel.ls.utils.httpException
import pt.isel.ls.utils.json


class SessionsAPI(private val services: SessionServices) {
    fun searchSessions(request: Request): Response {
        return try {
            /**
             * TODO maybe change to query
             **/
            Response(Status.OK)
                .json(services.searchSessions(
                    request.bodyString(),
                    request.header("Authorization"),
                    request.query("skip")?.toInt() ,
                    request.query("limit")?.toInt()
                ))
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun createSession(request: Request): Response {
        return try {
            Response(Status.CREATED)
                .json(services.createSession(
                    request.bodyString(),
                    request.header("Authorization")
                ))
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun getSession(request: Request): Response {
        return try {
            Response(Status.OK)
                .json(services.getSession(
                    request.path("sessionId")?.toInt(),
                    request.header("Authorization")
                ))
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun addPlayerToSession(request: Request): Response {
        return try {
            val id=services.addPlayerToSession(
                request.path("sessionId")?.toInt(),
                request.header("Authorization")
            )
            Response(Status.OK)
                .json("Added player $id to session")
        } catch (e: Exception) {
            httpException(e)
        }
    }
}
