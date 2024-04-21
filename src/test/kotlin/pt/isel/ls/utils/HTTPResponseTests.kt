package pt.isel.ls.utils

import org.http4k.core.Response
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import pt.isel.ls.api.APISchema
import pt.isel.ls.api.models.genres.GenreDetails
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.exceptions.BadRequestException
import pt.isel.ls.utils.exceptions.ForbiddenException
import kotlin.test.assertEquals

class HTTPResponseTests : APISchema() {
    @Test
    fun `json() returns response with body in json successfully`() {
        val testData = GenreDetails(Genre(1, "FPS"))

        val response = Response(Status.OK).json(testData)

        assertEquals("application/json", response.header("content-type"))
        assertEquals("""{"genreId":1,"name":"FPS"}""", response.bodyString())
    }

    @Test
    fun `httpException() returns the right response`() {
        val notFoundException = NoSuchElementException("Resource not found")
        val illegalArgumentException = IllegalArgumentException("Invalid argument")
        val authorizationException = AuthorizationException("Unauthorized access")
        val forbiddenException = ForbiddenException("Forbidden access")
        val badRequestException = BadRequestException("Conflict occurred")
        val otherException = RuntimeException("Some other error")

        val responseNotFound = httpException(notFoundException)
        val responseIllegalArgument = httpException(illegalArgumentException)
        val responseAuthorization = httpException(authorizationException)
        val responseForbidden = httpException(forbiddenException)
        val responseBadRequest = httpException(badRequestException)
        val responseOther = httpException(otherException)

        assertEquals(404, responseNotFound.status.code)
        assertEquals("application/json", responseNotFound.header("content-type"))

        assertEquals(400, responseIllegalArgument.status.code)
        assertEquals("application/json", responseIllegalArgument.header("content-type"))

        assertEquals(400, responseBadRequest.status.code)
        assertEquals("application/json", responseBadRequest.header("content-type"))

        assertEquals(401, responseAuthorization.status.code)
        assertEquals("application/json", responseAuthorization.header("content-type"))

        assertEquals(403, responseForbidden.status.code)
        assertEquals("application/json", responseForbidden.header("content-type"))

        assertEquals(500, responseOther.status.code)
        assertEquals("application/json", responseOther.header("content-type"))
    }
}
