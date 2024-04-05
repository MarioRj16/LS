package pt.isel.ls.api

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.SessionCreate
import pt.isel.ls.api.models.SessionSearch
import pt.isel.ls.api.models.SessionUpdate
import pt.isel.ls.services.SessionServices

class SessionsAPI(private val services: SessionServices) : APISchema() {
    fun searchSessions(request: Request): Response =
        useWithException {
            logRequest(request)
            val sessionSearch = Json.decodeFromString<SessionSearch>(request.bodyString())
            Response(Status.OK)
                .json(
                    services.searchSessions(
                        sessionSearch,
                        request.header("Authorization"),
                        request.query("skip")?.toInt() ?: DEFAULT_SKIP,
                        request.query("limit")?.toInt() ?: DEFAULT_LIMIT,
                    ),
                )
        }

    fun createSession(request: Request): Response =
        useWithException {
            logRequest(request)
            val sessionInput = Json.decodeFromString<SessionCreate>(request.bodyString())
            Response(Status.CREATED)
                .json(
                    services.createSession(
                        sessionInput,
                        request.header("Authorization"),
                    ),
                )
        }

    fun getSession(request: Request): Response =
        useWithException {
            logRequest(request)
            Response(Status.OK)
                .json(
                    services.getSession(
                        request.path("sessionId")?.toInt(),
                        request.header("Authorization"),
                    ),
                )
        }

    fun updateSession(request: Request): Response =
        useWithException {
            val sessionUpdate = Json.decodeFromString<SessionUpdate>(request.bodyString())
            Response(Status.OK)
                .json(
                    services.updateSession(
                        request.path("sessionId")?.toInt(),
                        sessionUpdate,
                        request.header("Authorization"),
                    ),
                )
        }

    fun deleteSession(request: Request): Response =
        useWithException {
            services.deleteSession(
                request.path("sessionId")?.toInt(),
                request.header("Authorization"),
            )
            Response(Status.NO_CONTENT).json("")
        }

    fun addPlayerToSession(request: Request): Response =
        useWithException {
            logRequest(request)
            val id =
                services.addPlayerToSession(
                    request.path("sessionId")?.toInt(),
                    request.header("Authorization"),
                )
            Response(Status.OK)
                .json("Added player $id to session")
        }

    fun removePlayerFromSession(request: Request): Response =
        useWithException {
            services.removePlayerFromSession(
                request.path("sessionId")?.toInt(),
                request.header("Authorization"),
                request.path("playerId")?.toInt(),
            )
            Response(Status.NO_CONTENT).json("")
        }
}
