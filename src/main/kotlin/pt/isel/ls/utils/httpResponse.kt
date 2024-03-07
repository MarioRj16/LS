package pt.isel.ls.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.server.students


fun httpResponse(body:Any):Response{
    return Response(Status.OK)
        .header("content-type", "application/json")
        .body(Json.encodeToString(body))
            //students.find { it.number == stdNumber })
}