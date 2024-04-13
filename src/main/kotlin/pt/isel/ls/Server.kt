package pt.isel.ls

import org.http4k.server.Jetty
import org.http4k.server.asServer
import pt.isel.ls.api.API
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.services.Services

fun main() {
    /**
     * if there isn't any env var with the name defined in our config it will run by default local mem
     **/
    //val db = DataPostgres(System.getenv(CONN_NAME))
    val db= DataMem()
     //db.reset()
    // db.delete()
    // db.create()
     //db.populate()

    val api = API(Services(db))
    val jettyServer = Routes(api).app.asServer(Jetty(PORT)).start()
    logger.info("server started listening")
    readln()
    jettyServer.stop()

    logger.info("leaving Main")
}
