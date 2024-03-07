package pt.isel.ls.Data

import pt.isel.ls.Domain.PlayerSession

interface PlayerSessionsStorage {
    fun create(playerId: Int, sessionId: Int): PlayerSession

    fun search(playerId: Int, sessionId: Int): Set<PlayerSession>
}