package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import pt.isel.ls.services.SessionServices


class SessionsAPI(private val services: SessionServices) : APISchema() {

    /**
     * TODO maybe change to query
     **/
    fun searchSessions(request: Request): Response = useWithException {
        Response(Status.OK)
            .json(
                services.searchSessions(
                    request.bodyString(),
                    request.header("Authorization"),
                    request.query("skip")?.toInt(),
                    request.query("limit")?.toInt()
                )
            )
    }

    fun createSession(request: Request): Response = useWithException {
        Response(Status.CREATED)
            .json(
                services.createSession(
                    request.bodyString(),
                    request.header("Authorization")
                )
            )
    }

    fun getSession(request: Request): Response = useWithException {
        Response(Status.OK)
            .json(
                services.getSession(
                    request.path("sessionId")?.toInt(),
                    request.header("Authorization")
                )
            )
    }

    fun addPlayerToSession(request: Request): Response = useWithException {
        val id = services.addPlayerToSession(
            request.path("sessionId")?.toInt(),
            request.header("Authorization")
        )
        Response(Status.OK)
            .json("Added player $id to session")
    }
}
