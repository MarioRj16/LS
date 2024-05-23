package pt.isel.ls.api.models.games

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.PaginatedResponse

@Serializable
class GameListResponse private constructor(
    val games: List<GameResponse>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int,
) {
    companion object {
        operator fun invoke(gamesResponse: PaginatedResponse<GameResponse>): GameListResponse {
            val (games, hasNext, hasPrevious) = gamesResponse
            return GameListResponse(
                games,
                hasNext,
                hasPrevious,
                games.size,
            )
        }
    }
}
