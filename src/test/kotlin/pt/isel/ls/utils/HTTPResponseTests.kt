package pt.isel.ls.utils

import org.http4k.core.Response
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.exceptions.ForbiddenException
import kotlin.test.assertEquals

class HTTPResponseTests {

    @Test
    fun `json() returns response with body in json successfully`(){

        val testData = Genre("FPS")

        val response = Response(Status.OK).json(testData)

        assertEquals("application/json", response.header("content-type"))
        assertEquals("""{"genre":"FPS"}""", response.bodyString())
    }

    @Test
    fun `httpException() returns the right response`(){
        val notFoundException = NoSuchElementException("Resource not found")
        val illegalArgumentException = IllegalArgumentException("Invalid argument")
        val authorizationException = AuthorizationException("Unauthorized access")
        val forbiddenException = ForbiddenException("Forbidden access")
        val conflictException = ConflictException("Conflict occurred")
        val otherException = RuntimeException("Some other error")

        val responseNotFound = httpException(notFoundException)
        val responseIllegalArgument = httpException(illegalArgumentException)
        val responseAuthorization = httpException(authorizationException)
        val responseForbidden = httpException(forbiddenException)
        val responseConflict = httpException(conflictException)
        val responseOther = httpException(otherException)

        assertEquals(404, responseNotFound.status.code)
        assertEquals("application/json", responseNotFound.header("content-type"))
        assertEquals("{\"message\":\"Resource not found\"}", responseNotFound.bodyString())
        assertEquals(400, responseIllegalArgument.status.code)
        assertEquals("application/json", responseIllegalArgument.header("content-type"))
        assertEquals("{\"message\":\"Invalid argument\"}", responseIllegalArgument.bodyString())

        assertEquals(401, responseAuthorization.status.code)
        assertEquals("application/json", responseAuthorization.header("content-type"))
        assertEquals("{\"message\":\"Unauthorized access\"}", responseAuthorization.bodyString())

        assertEquals(403, responseForbidden.status.code)
        assertEquals("application/json", responseForbidden.header("content-type"))
        assertEquals("{\"message\":\"Forbidden access\"}", responseForbidden.bodyString())

        assertEquals(409, responseConflict.status.code)
        assertEquals("application/json", responseConflict.header("content-type"))
        assertEquals("{\"message\":\"Conflict occurred\"}", responseConflict.bodyString())

        assertEquals(500, responseOther.status.code)
        assertEquals("application/json", responseOther.header("content-type"))
        assertEquals("{\"message\":\"Server Error\"}", responseOther.bodyString())
    }
}