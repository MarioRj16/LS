package pt.isel.ls

object DataMemoryStorage: Storage{
    // TODO: ask if it's okay to have points of mutability here
    var players = 0 to HashMap<String, Player>() // Hashmap<email, player>
    var genres = HashMap<String, Void>()
    var playersSessions = setOf<Pair<Int, Int>>()
    var gamingSessions = 0 to HashMap<Int, GamingSession>()
    var games = 0 to HashMap<Int, Game>()
    var gamesGenres = setOf<Pair<Int, Int>>()

    override fun createPlayer(name: String, email: String): Pair<Int, String> {
        TODO("Not yet implemented")
    }

    override fun getPlayer(token: String): Player {
        TODO("Not yet implemented")
    }

    override fun createGame(name: String, developer: String, genres: Set<Genre>): Int {
        TODO("Not yet implemented")
    }

    override fun getGame(name: String): Game {
        TODO("Not yet implemented")
    }

    override fun listGames(developer: String, genres: Set<Genre>): List<Game> {
        TODO("Not yet implemented")
    }

    override fun createSession(capacity: Int, game: Int, date: String) {
        TODO("Not yet implemented")
    }

    override fun getSession(sessionId: Int): GamingSession {
        TODO("Not yet implemented")
    }

    override fun listSessions(game: Int, date: String?, isOpen: Boolean?, player: Int?): List<GamingSession> {
        TODO("Not yet implemented")
    }

    override fun addPlayerToSession(session: Int, player: Int): Boolean {
        TODO("Not yet implemented")
    }

}