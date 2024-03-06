package pt.isel.ls

import pt.isel.ls.models.Game
import pt.isel.ls.models.GamingSession
import pt.isel.ls.models.Genre
import pt.isel.ls.models.Player
import pt.isel.ls.utils.storageTable

object DataMemoryStorage: Storage{
    // TODO: ask if it's okay to have points of mutability here
    var playersByEmail = storageTable(0, HashMap<String, Player>()) // Hashmap<email, player>
    var playersByToken = storageTable(0, HashMap<String, Player>()) // Hashmap<token, player>
    var genres = setOf<String>()
    var playersSessions = setOf<Pair<Int, Int>>()
    var gamingSessions = storageTable(0, HashMap<Int, GamingSession>())
    var games = storageTable(0, HashMap<String, Game>()) // Hashmap<name, game>
    var gamesGenres = setOf<Pair<Int, String>>()

    override fun createPlayer(name: String, email: String): Pair<Int, String> {
        require(email !in playersByEmail.hashmap){"$email is already registered"}
        val player = Player(ID = playersByEmail.counter, email = email, name = name)
        playersByEmail = playersByEmail.copy(counter = playersByEmail.counter + 1)
        playersByEmail.hashmap[email] = player
        playersByToken = playersByToken.copy(counter = playersByToken.counter + 1)
        playersByToken.hashmap[player.token] = player
        return player.ID to player.token
        // TODO: Find out if its okay to have two hashmaps
    }

    override fun getPlayer(token: String): Player? {
        return playersByToken.hashmap[token]
    }

    override fun createGame(name: String, developer: String, genres: Set<Genre>): Int {
        require(name !in games.hashmap){"$name is already registered"}
        games = games.copy(counter = games.counter + 1)
        games.hashmap[name] = Game(ID = games.counter, name = name, developer = developer)
        for (i in genres)
            gamesGenres += games.hashmap[name]!!.ID to i.name
        
        return games.counter
    }

    override fun getGame(name: String): Game? {
        return games.hashmap[name]
    }

    override fun listGames(developer: String?, genres: Set<Genre>?): List<Game> {
        return when{
            developer.isNullOrBlank() && genres.isNullOrEmpty() -> games.hashmap.values.toList()
            genres.isNullOrEmpty() -> games.hashmap.values.toList().filter { it.developer == developer }
            else ->
                games.hashmap.values.toList().filter { it.ID in (gamesGenres.filter { (game, genre) -> genre in genres }) }

        }
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