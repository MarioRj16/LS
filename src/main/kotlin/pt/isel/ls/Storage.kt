package pt.isel.ls

interface Storage {
    fun createPlayer(name: String, email: String): Pair<Int, String>

    fun getPlayer(token: String): Player

    fun createGame(name: String, developer: String, genres: Set<Genre>): Int

    fun getGame(name: String): Game

    fun listGames(developer: String, genres: Set<Genre>): List<Game>

    fun createSession(capacity: Int, game: Int, date: String)
    // TODO: Find out if sessionDate should be a string or Date

    fun getSession(sessionId: Int): GamingSession

    fun listSessions(game: Int, date: String?, isOpen: Boolean?, player: Int?): List<GamingSession>

    fun addPlayerToSession(session: Int, player: Int): Boolean

}