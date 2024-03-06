package pt.isel.ls.Data

import java.util.*

interface TokenStorage {
    fun create(): UUID

    fun get(token: UUID): Int
}