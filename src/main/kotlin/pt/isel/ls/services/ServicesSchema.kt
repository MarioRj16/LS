package pt.isel.ls.services

import pt.isel.ls.data.Data
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.AuthorizationException
import java.util.*

abstract class ServicesSchema(internal val data: Data) {

    fun <T> withAuthorization(token: UUID, action: (Player) -> T): T {
        val user = bearerToken(token)
        return action(user)
    }

    protected fun bearerToken(token: UUID): Player {
        try {
            return data.players.getByToken(token)
        } catch (e: NoSuchElementException) {
            throw AuthorizationException()
        }
    }
}
