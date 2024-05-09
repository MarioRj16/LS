package pt.isel.ls.data.sessions

interface SessionTests {
    fun createReturnsGamingSessionSuccessfully()

    fun getReturnsGamingSessionSuccessfully()

    fun getReturnsNullForNonExistingGamingSession()

    fun addPlayerAddsPlayerToGamingSessionSuccessfully()

    fun removePlayerRemovesPlayerFromSessionSuccessfully()

    fun updateUpdatesGamingSessionSuccessfully()

    fun deleteDeletesGamingSessionSuccessfully()

    fun isOwnerChecksIfPlayerIsOwnerSuccessfully()

    fun searchByGameReturnsGamingSessionsSuccessfully()

    fun searchByPlayerEmailReturnsGamingSessionsSuccessfully()

    fun searchByDateReturnsGamingSessionsSuccessfully()

    fun searchByStateReturnsGamingSessionsSuccessfully()
}
