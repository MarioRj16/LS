package pt.isel.ls.services

import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.AuthorizationException
import java.util.*

abstract class ServicesSchema(protected val data: Data) {

    fun <T> withAuthorization(token: UUID, action: (Player) -> T): T {
        val user = bearerToken(token)
        return action(user)
    }

    protected fun bearerToken(token: UUID): Player {
        return data.players.get(token)
            ?: throw AuthorizationException("Missing or invalid bearer token")
    }
}
