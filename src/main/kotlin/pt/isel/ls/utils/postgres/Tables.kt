package pt.isel.ls.utils.postgres

import kotlinx.datetime.toKotlinLocalDateTime
import pt.isel.ls.domain.Game
import pt.isel.ls.domain.GamingSession
import pt.isel.ls.domain.Genre
import pt.isel.ls.domain.Player
import java.sql.ResultSet
import java.util.*

fun ResultSet.toPlayer(): Player {
    return Player(
        id = getInt("player_id"),
        name = getString("player_name"),
        email = getString("email"),
        token = UUID.fromString(getString("token")),
    )
}

fun ResultSet.toGenre(): Genre = Genre(getInt("genre_id"), getString("genre"))

fun ResultSet.toGame(genres: Set<Genre>): Game =
    Game(
        id = getInt("game_id"),
        name = getString("game_name"),
        developer = getString("developer"),
        genres = genres,
    )

fun ResultSet.toPreviousGame(
    genres: Set<Genre>,
    gameId: Int,
): Game =
    Game(
        id = gameId,
        name = getString("game_name"),
        developer = getString("developer"),
        genres = genres,
    )

fun ResultSet.toGamingSession(players: Set<Player>): GamingSession =
    GamingSession(
        id = getInt("gaming_session_id"),
        gameId = getInt("game"),
        creatorId = getInt("creator"),
        maxCapacity = getInt("capacity"),
        startingDate = getTimestamp("starting_date").toLocalDateTime().toKotlinLocalDateTime(),
        players = players,
    )
