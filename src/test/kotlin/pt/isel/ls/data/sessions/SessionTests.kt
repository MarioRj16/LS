package pt.isel.ls.data.sessions

interface SessionTests {
    fun `create() returns gaming session successfully`()

    fun `get() returns gaming session successfully`()

    fun `get() returns null for non existing gaming session`()

    fun `addPlayer() adds player to gaming session successfully`()

    fun `removePlayer() removes player from session successfully`()

    fun `update() updates gaming session successfully`()

    fun `delete() deletes gaming session successfully`()

    fun `isOwner() checks if player is owner successfully`()

    fun `search() by game returns gaming sessions successfully`()

    fun `search() by player email returns gaming sessions successfully`()

    fun `search() by date returns gaming sessions successfully`()

    fun `search() by state returns gaming sessions successfully`()
}