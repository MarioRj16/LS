package pt.isel.ls.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status

fun httpResponse(body:Any?,status:Status):Response{
    /**
     * maybe add exception when receive body null
     */
    return Response(status)
        .header("content-type", "application/json")
        .body(Json.encodeToString(body))
}

fun httpError(e:Exception):Response{
    val message=e.message!!.split("-")
    return httpResponse(message[1],httpStatus(message[0]))
}

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
