package pt.isel.ls.api

import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.api.models.sessions.SessionCreate
import pt.isel.ls.api.models.sessions.SessionSearch
import pt.isel.ls.api.models.sessions.SessionUpdate
import pt.isel.ls.services.SessionsServices
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.isPositive
import pt.isel.ls.utils.toLocalDateTime
import pt.isel.ls.utils.validateInt

class SessionsAPI(private val services: SessionsServices) : APISchema() {
    fun searchSessions(request: Request): Response =
        request.useWithException { token ->
            val gameId = request.query("gameId")?.toIntOrNull()
            val query = request.query("date")?.toLong()
            val date = query?.toLocalDateTime()
            val state = request.query("state")?.toBoolean()
            val player = request.query("player")
            val hostId = request.query("host")?.toIntOrNull()
            val sessionSearch = SessionSearch(gameId, date, state, player, hostId)
            val skip = request.query("skip")?.toInt().validateInt(DEFAULT_SKIP) { it.isNotNegative() }
            val limit = request.query("limit")?.toInt().validateInt(DEFAULT_LIMIT) { it.isNotNegative() }
            Response(Status.OK)
                .json(
                    services.searchSessions(sessionSearch, token, skip, limit),
                )
        }

    fun createSession(request: Request): Response =
        request.useWithException { token ->
            // val sessionInput = Json.decodeFromString<SessionCreate>(request.bodyString())
            val body = request.bodyString()

            val gameId = "\"gameId\":\\s*(\\d+)".toRegex().find(body)?.groups?.get(1)?.value?.toInt()
            val capacity = "\"capacity\":\\s*(\\d+)".toRegex().find(body)?.groups?.get(1)?.value?.toInt()
            var startingDate = "\"startingDate\":\\s*\"([^\"]*)\"".toRegex().find(body)?.groups?.get(1)?.value
            if (startingDate == null) {
                startingDate = "\"startingDate\":\\s*(\\d+)".toRegex().find(body)?.groups?.get(1)?.value
            }

            if (gameId == null || capacity == null || startingDate == null) {
                throw IllegalArgumentException("Invalid request body")
            }

            val date = if (startingDate.contains('T')) {
                startingDate.toLocalDateTime()
            } else {
                startingDate.toLong().toLocalDateTime()
            }

            val sessionInput = SessionCreate(gameId, capacity, date)

            Response(Status.CREATED)
                .json(
                    services.createSession(sessionInput, token),
                )
        }

    fun getSession(request: Request): Response =
        request.useWithException { token ->
            val sessionId = request.path("sessionId")?.toInt().validateInt { it.isPositive() }
            Response(Status.OK)
                .json(services.getSession(sessionId, token))
        }

    fun updateSession(request: Request): Response =
        request.useWithException { token ->
            val sessionUpdate = Json.decodeFromString<SessionUpdate>(request.bodyString())
            val sessionId = request.path("sessionId")?.toInt().validateInt { it.isPositive() }
            Response(Status.OK)
                .json(services.updateSession(sessionId, sessionUpdate, token))
        }

    fun deleteSession(request: Request): Response =
        request.useWithException { token ->
            val sessionId = request.path("sessionId")?.toInt().validateInt { it.isPositive() }
            services.deleteSession(sessionId, token)
            Response(Status.NO_CONTENT)
        }

    fun addPlayerToSession(request: Request): Response =
        request.useWithException { token ->
            val sessionId = request.path("sessionId")?.toInt().validateInt { it.isPositive() }
            val id = services.addPlayerToSession(sessionId, token)
            Response(Status.OK)
                .json("Added player $id to session")
        }

    fun removePlayerFromSession(request: Request): Response =
        request.useWithException { token ->
            val sessionId = request.path("sessionId")?.toInt().validateInt { it.isPositive() }
            val playerId = request.path("playerId")?.toInt().validateInt { it.isPositive() }
            services.removePlayerFromSession(sessionId, token, playerId)
            Response(Status.NO_CONTENT)
        }
}
