package pt.isel.ls.Data

import pt.isel.ls.Domain.Player

interface PlayerStorage {
    fun create(name: String, email: String): Player

    fun get(id: Int): Player?

    fun emailExists(email: String): Boolean
    //  TODO: find out if this should exist or if we should let the DB throw an exception and then catch it
}