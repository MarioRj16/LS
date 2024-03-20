package pt.isel.ls.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.api.models.ExceptionResponse
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.exceptions.ForbiddenException


//TODO: does it make sense to have this as inline fun?
inline fun <reified T> Response.json(body: T): Response{
    return this
        .header("content-type", "application/json")
        .body(Json.encodeToString(body))
}


/*
fun httpStatus(code:String):Status{
    return when(code){
        "200" -> Status.OK
        "201" -> Status.CREATED     
        "400" -> Status.BAD_REQUEST
        "401" -> Status.UNAUTHORIZED
        "403" -> Status.FORBIDDEN
        "404" -> Status.NOT_FOUND
        "409" -> Status.CONFLICT
        else -> Status.INTERNAL_SERVER_ERROR
    }
}
 */
fun httpException(e: Exception):Response{
    val serverErrorMsg = "Server Error"
    val body = ExceptionResponse(e.message ?: serverErrorMsg)
    return when (e){
        is NoSuchElementException -> Response(Status.NOT_FOUND).json(body)
        is IllegalArgumentException -> Response(Status.BAD_REQUEST).json(body)
        is AuthorizationException -> Response(Status.UNAUTHORIZED).json(body)
        is ForbiddenException -> Response(Status.FORBIDDEN).json(body)
        is ConflictException -> Response(Status.CONFLICT).json(body)
        else -> Response(Status.INTERNAL_SERVER_ERROR).json(body.copy(message = serverErrorMsg))
    }
}
