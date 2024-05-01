package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Game

@Serializable
class GameListResponse private constructor(
    val games: List<GameResponse>,
    val total: Int,
) {
    companion object {
        operator fun invoke(games: List<Game>): GameListResponse {
            return GameListResponse(
                games.map { game -> GameResponse(game) },
                games.size
            )
        }
    }
}
