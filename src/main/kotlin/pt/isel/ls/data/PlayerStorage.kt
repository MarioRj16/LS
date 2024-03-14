package pt.isel.ls.data

import pt.isel.ls.domain.Player

interface PlayerStorage {
    fun create(name: String, email: String): Player

    fun get(id: Int): Player
}