package pt.isel.ls.integration.sections

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pt.isel.ls.api.models.genres.GenreListResponse
import pt.isel.ls.integration.IntegrationTests
import kotlin.test.assertEquals

class GenresTests : IntegrationTests() {

    @BeforeEach
    fun setUp() {
        db.reset()
    }

    @Test
    fun `getGenres returns 200 for good request`() {
        val player = playerFactory.createRandomPlayer()
        val request =
            Request(Method.GET, "$URI_PREFIX/genres")
                .json("")
                .token(player.token)

        client(request)
            .apply {
                val nrOfGenres = db.genres.getAllGenres().size
                assertEquals(Status.OK, status)
                assertEquals(nrOfGenres, Json.decodeFromString<GenreListResponse>(bodyString()).total)
            }
    }
}
