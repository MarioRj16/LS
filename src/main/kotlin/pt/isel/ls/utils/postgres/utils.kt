package pt.isel.ls.utils.postgres

import kotlinx.datetime.*
import pt.isel.ls.domain.*
import java.io.File
import java.sql.Connection
import java.sql.ResultSet
import java.util.*

fun ResultSet.toPlayer(): Player =
    Player(
        id = getInt("player_id"),
        name = getString("player_name"),
        email = getString("email"),
        token = UUID.fromString(getString("token"))
    )

fun ResultSet.toGenre(): Genre = Genre(genre = getString("genre"))

fun ResultSet.toGame(genres: Set<Genre>): Game =
    Game(
        id = getInt("game_id"),
        name = getString("game_name"),
        developer = getString("developer"),
        genres = genres
    )

fun ResultSet.toGamingSession(players: Set<Player>): GamingSession =
    GamingSession(
        id = getInt("gaming_session_id"),
        game = getInt("game"),
        maxCapacity = getInt("capacity"),
        startingDate = getTimestamp("starting_date").toLocalDateTime().toKotlinLocalDateTime(),
        players = players
    )

inline fun <R> Connection.useWithRollback(block: (Connection) -> R): R {
    try {
        return block(this)
    } catch (e: Throwable) {
        rollback() // TODO: Find out if we need to handle rollback failures
        throw e
    } finally {
        commit()
        close() // TODO: Find out if we need to handle close failures
    }
}

fun Connection.runSQLScript(path: String){
    val script = File(path).readText()
    prepareStatement(script).executeUpdate()
}