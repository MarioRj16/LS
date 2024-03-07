package pt.isel.ls.Data

import pt.isel.ls.Domain.Player

interface PlayerStorage {
    fun create(name: String, email: String): Int

    fun search(id: Int): Player

}