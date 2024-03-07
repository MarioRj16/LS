package pt.isel.ls.server.services

import kotlinx.serialization.json.Json

fun serviceCreatePlayer(input:String){
    val playerInput=Json.decodeFromString<PlayerInput>(input)

}

fun serviceGetPlayer(id:)