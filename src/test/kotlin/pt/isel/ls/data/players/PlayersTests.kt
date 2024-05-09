package pt.isel.ls.data.players

interface PlayersTests {
    fun createCreatesPlayerSuccessfully()

    fun getReturnsPlayerSuccessfully()

    fun getReturnsNullForNonExistingPlayer()

    fun searchReturnsPlayersSuccessfully()

    fun searchByNameReturnsPlayersSuccessfully()

    fun searchByPartialNameReturnsPlayersSuccessfully()
}
