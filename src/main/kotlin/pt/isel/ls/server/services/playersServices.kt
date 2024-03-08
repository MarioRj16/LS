package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Domain.PlayerCreate
import pt.isel.ls.Data.Mem.PlayersMem
fun serviceCreatePlayer(input:String){
    val playerInput=Json.decodeFromString<PlayerCreate>(input)


}

fun serviceGetPlayer(id:String){

    
}