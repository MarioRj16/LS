package pt.isel.ls.data

import pt.isel.ls.domain.Player
import java.util.*

interface PlayerStorage {
    fun create(name: String, email: String): Player

    fun get(id: Int): Player

    fun getByToken(token: UUID): Player
}