package pt.isel.ls

import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.api.API
import pt.isel.ls.data.postgres.DataPostgres
import pt.isel.ls.services.Services

private val logger = LoggerFactory.getLogger("pt.isel.ls")

/**
 *
 * TODO to implement in the future
 *
fun getDate(request: Request): Response {
 return Response(OK)
 .header("content-type", "text/plain")
 .body(Clock.System.now().toString())
}

fun logRequest(request: Request) {
 logger.info(
 "incoming request: method={}, uri={}, content-type={} accept={}",
 request.method,
 request.uri,
 request.header("content-type"),
 request.header("accept"),
 )
}
*/

fun main() {
    /**
     * if there isn't any env var with the name defined in our config it will run by default local mem
     **/
    val db = DataPostgres(System.getenv(CONN_NAME))

    // db.reset()
    // db.delete()
    // db.create()
    // db.populate()

    val api = API(Services(db))
    val jettyServer = Routes(api).app.asServer(Jetty(PORT)).start()
    logger.info("server started listening")
    readln()
    jettyServer.stop()

    logger.info("leaving Main")
}
