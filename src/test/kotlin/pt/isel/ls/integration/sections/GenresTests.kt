package pt.isel.ls.integration.sections

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.genres.GenreListResponse
import pt.isel.ls.integration.IntegrationTests
import kotlin.test.assertEquals

class GenresTests: IntegrationTests() {

    @Test
    fun getGenres() {
        val request =
            Request(Method.GET, "$URI_PREFIX/genres")
                .json("")
                .token(user!!.token)

        client(request)
            .apply {
                val nrOfGenres = db.genres.getAllGenres().size
                assertEquals(Status.OK, status)
                assertEquals(nrOfGenres, Json.decodeFromString<GenreListResponse>(bodyString()).total)
            }

    }
}