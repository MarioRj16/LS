package pt.isel.ls.services

import java.util.*
import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.AuthorizationException

abstract class ServicesSchema(internal val data: Data) {

    fun <T> withAuthorization(token: UUID, action: (Player) -> T): T {
        val user = bearerToken(token)
        return action(user)
    }

    protected fun bearerToken(token: UUID): Player {
        return data.players.get(token)
            ?: throw AuthorizationException("Missing or invalid bearer token")
    }
}
