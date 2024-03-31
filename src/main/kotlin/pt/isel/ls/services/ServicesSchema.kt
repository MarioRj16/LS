package pt.isel.ls.services

import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.AuthorizationException
import java.util.*

abstract class ServicesSchema {
    fun bearerToken(
        authorization: String?,
        db: Data,
    ): Player {
        if (authorization.isNullOrEmpty() ||
            !authorization.startsWith("Bearer")
        ) {
            throw AuthorizationException("Missing Bearer token")
        }
        val token = authorization.removePrefix("Bearer ")
        return db.players.getByToken(UUID.fromString(token))
    }
}
