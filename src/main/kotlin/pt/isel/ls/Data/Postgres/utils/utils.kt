package pt.isel.ls.Data.Postgres.utils

import kotlinx.datetime.*
import pt.isel.ls.Domain.*
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.*

fun ResultSet.toPlayer(): Player =
    Player(
        id = getInt("player_id"),
        name = getString("player_name"),
        email = getString("email"),
        token = UUID.fromString(getString("token"))
    )

fun ResultSet.toGenre(): Genre =
    Genre(
        genre = getString("genre")
    )

fun ResultSet.toGame(genres: Set<Genre>): Game =
    Game(
        id = getInt("game_id"),
        name = getString("game_name"),
        developer = getString("developer"),
        genres = genres
    )

fun ResultSet.toGamingSession(players: Set<Player>): GamingSession{

    return GamingSession(
        id = getInt("gaming_session_id"),
        game = getInt("game"),
        capacity = getInt("capacity"),
        startingDate = getTimestamp("starting_date").toLocalDateTime().toKotlinLocalDateTime(),
        players = players
    )
}

fun LocalDateTime.toTimeStamp(): Timestamp = Timestamp.valueOf(toJavaLocalDateTime())

fun emailIsValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    return emailRegex.matches(email)
}

inline fun <R> Connection.useWithRollback(block: (Connection) -> R): R {
    try {
        return block(this)
    } catch (e: Throwable) {
        this.rollback() // TODO: Find out if we need to handle rollback failures
        throw e
    } finally {
        this.commit()
        this.close() // TODO: Find out if we need to handle close failures
    }
}
