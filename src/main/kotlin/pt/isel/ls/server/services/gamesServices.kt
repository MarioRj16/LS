package pt.isel.ls.server.services

import kotlinx.serialization.json.Json
import pt.isel.ls.Domain.GameCreate
import pt.isel.ls.Domain.GameSearch


fun serviceSearchGames(input:String){
    val gameInput= Json.decodeFromString<GameSearch>(input)
    search(gameInput.genres,gameInput.developer)
}

fun serviceCreateGame(input:String){
    val gameInput= Json.decodeFromString<GameCreate>(input)
    create(gameInput.name,gameInput.genres,gameInput.developer)
}

fun serviceGetGame(id:Int?){
     search(id)
}