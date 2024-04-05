package pt.isel.ls.services

import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.AuthorizationException
import java.util.*

abstract class ServicesSchema(internal val data: Data) {

    fun <T> withAuthorization(authorization: String?, action: (Player) -> T): T {
        val user = bearerToken(authorization)
        return action(user)
    }

    protected fun bearerToken(authorization: String?): Player {
        if (authorization.isNullOrEmpty() || !authorization.startsWith("Bearer")) {
            throw AuthorizationException("Missing Bearer token")
        }

        val token = authorization.removePrefix("Bearer ")
        return data.players.getByToken(UUID.fromString(token))
    }

}
