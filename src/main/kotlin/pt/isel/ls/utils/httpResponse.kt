package pt.isel.ls.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.exceptions.ForbiddenException


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
    return when (e){
        is NoSuchElementException -> Response(Status.NOT_FOUND).json(e.message)
        is IllegalArgumentException -> Response(Status.BAD_REQUEST).json("Illegal argument " + e.message)
        is AuthorizationException -> Response(Status.UNAUTHORIZED).json(e.message)
        is ForbiddenException -> Response(Status.FORBIDDEN).json(e.message)
        is ConflictException -> Response(Status.CONFLICT).json(e.message)
        else -> Response(Status.INTERNAL_SERVER_ERROR).json("Server Error")
    }
}
