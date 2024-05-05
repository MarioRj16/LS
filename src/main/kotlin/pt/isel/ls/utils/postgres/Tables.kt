package pt.isel.ls.utils.postgres

import java.sql.ResultSet
import java.util.*
import kotlinx.datetime.toKotlinLocalDateTime
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player
import pt.isel.ls.domain.Session
import pt.isel.ls.utils.Email

/**
 * Converts a [ResultSet] into a [Player] object.
 *
 * @return The converted [Player] object.
 */
fun ResultSet.toPlayer(): Player {
    return Player(
        id = getInt("player_id"),
        name = getString("player_name"),
        email = Email(getString("email")),
        token = UUID.fromString(getString("token")),
    )
}

/**
 * Converts a [ResultSet] into a [Genre] object.
 *
 * @return The converted [Genre] object.
 */
fun ResultSet.toGenre(): Genre = Genre(getInt("genre_id"), getString("genre_name"))

/**
 * Converts a [ResultSet] into a [Game] object.
 *
 * @param genres Set of genres associated with the game.
 * @param game Optional game ID. Defaults to the value retrieved from the ResultSet.
 * @return The converted [Game] object.
 */
fun ResultSet.toGame(genres: Set<Genre>, game: Int = getInt("game_id")): Game =
    Game(
        id = game,
        name = getString("game_name"),
        developer = getString("developer"),
        genres = genres,
    )

/**
 * Converts a [ResultSet] into a [Session] object.
 *
 * @param players Set of players participating in the gaming session.
 * @return The converted [Session] object.
 */
fun ResultSet.toGamingSession(players: Set<Player>): Session =
    Session(
        id = getInt("gaming_session_id"),
        gameId = getInt("game_id"),
        hostId = getInt("host"),
        maxCapacity = getInt("capacity"),
        startingDate = getTimestamp("starting_date").toLocalDateTime().toKotlinLocalDateTime(),
        players = players,
    )
