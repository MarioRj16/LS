package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Game
import pt.isel.ls.utils.PaginateResponse

@Serializable
class GameListResponse private constructor(
    val games: List<GameResponse>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int,
) {
    companion object {
        operator fun invoke(gamesResponse: PaginateResponse<Game>): GameListResponse {
            val (games, hasNext, hasPrevious) = gamesResponse
            return GameListResponse(
                games.map { game -> GameResponse(game) },
                hasNext,
                hasPrevious,
                games.size,
            )
        }
    }
}
