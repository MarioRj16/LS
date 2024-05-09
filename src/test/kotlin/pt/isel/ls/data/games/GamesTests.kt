package pt.isel.ls.data.games

interface GamesTests {
    fun createReturnGameSuccessfully()

    fun getReturnsGameSuccessfully()

    fun getReturnsNullForNonExistingGame()

    fun getByIdReturnsGameSuccessfully()

    fun getByIdReturnsNullForNonExistingGame()

    fun searchWithNoGamesReturnsEmptyList()

    fun searchReturnsAllGamesSuccessfully()

    fun searchByNameReturnsGamesSuccessfully()

    fun searchByCaseInsensitiveNameReturnsGamesSuccessfully()

    fun searchByPartialNameReturnsGamesSuccessfully()

    fun searchByDeveloperReturnsGamesSuccessfully()

    fun searchByGenreReturnsGamesSuccessfully()
}
