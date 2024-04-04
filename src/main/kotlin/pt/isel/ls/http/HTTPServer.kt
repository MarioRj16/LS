package pt.isel.ls.http

import RoutesSite
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.*
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.Routes
import pt.isel.ls.SITE_PORT
import pt.isel.ls.logger



fun getDate(request: Request): Response {
    return Response(OK)
            .header("content-type", "text/plain")
            .body(Clock.System.now().toString())
}



fun main() {
    val jettyServer = RoutesSite().app.asServer(Jetty(SITE_PORT)).start()
    logger.info("server started listening")

    readln()
    jettyServer.stop()

    logger.info("leaving Main")
}