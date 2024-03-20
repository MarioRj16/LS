package pt.isel.ls.api

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.services.Services
import pt.isel.ls.utils.exceptions.AuthorizationException
import pt.isel.ls.utils.exceptions.ConflictException
import pt.isel.ls.utils.exceptions.ForbiddenException

open class API(services:Services):APISchema(){
    val playerAPI=PlayersAPI(services.playersServices)
    val gamesAPI=GamesAPI(services.gamesServices)
    val sessionsAPI=SessionsAPI(services.gamingSessionsServices)

}