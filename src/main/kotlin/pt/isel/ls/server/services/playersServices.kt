package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Domain.PlayerCreate
import pt.isel.ls.Data.Mem.PlayersMem
import pt.isel.ls.Data.Storage

fun serviceCreatePlayer(input:String){
    val playerInput=Json.decodeFromString<PlayerCreate>(input)
    Storage.players
    screate(playerInput.name,playerInput.email)

}

fun serviceGetPlayer(id:Int?){
    PlayersMem().search(id)
    
}