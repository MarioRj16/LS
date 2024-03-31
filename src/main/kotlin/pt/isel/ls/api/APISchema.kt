package pt.isel.ls.api

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import org.postgresql.util.PSQLException
import pt.isel.ls.api.models.ExceptionResponse
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.exceptions.ForbiddenException

abstract class APISchema {
    inline fun <reified T> Response.json(body: T): Response {
        return this
            .header("content-type", "application/json")
            .body(Json.encodeToString(body))
    }

    inline fun useWithException(block: () -> Response): Response {
        return try {
            block()
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun httpException(e: Exception): Response {
        return when (e) {
            is NoSuchElementException -> Response(Status.NOT_FOUND).json(ExceptionResponse(e.message))
            is IllegalArgumentException -> Response(Status.BAD_REQUEST).json(ExceptionResponse(e.message))
            is AuthorizationException -> Response(Status.UNAUTHORIZED).json(ExceptionResponse(e.message))
            is ForbiddenException -> Response(Status.FORBIDDEN).json(ExceptionResponse(e.message))
            is ConflictException -> Response(Status.CONFLICT).json(ExceptionResponse(e.message))
            is PSQLException -> Response(Status.CONFLICT).json(ExceptionResponse(e.message))
            else -> Response(Status.INTERNAL_SERVER_ERROR).json(ExceptionResponse(e.message))
        }
    }
}
