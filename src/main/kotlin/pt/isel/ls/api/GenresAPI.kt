package pt.isel.ls.api

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.services.GenresServices

class GenresAPI(private val services: GenresServices): APISchema() {

    fun getGenres(request: Request): Response =
        request.useWithException { token ->
            Response(Status.OK).json(services.getGenres(token))
        }
}