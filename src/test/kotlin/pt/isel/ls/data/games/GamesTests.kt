package pt.isel.ls.data.games

interface GamesTests {
    fun `create() return game successfully`()

    fun `get() returns game successfully`()

    fun `get() returns null for non existing game`()

    fun `getById() returns game successfully`()

    fun `getById() returns null for non existing game`()

    fun `search() with no games returns empty list`()

    fun `search() returns all games successfully`()

    fun `search() by name returns games successfully`()

    fun `search() by case-insensitive name returns games successfully`()

    fun `search() by partial name returns games successfully`()

    fun `search() by developer returns games successfully`()

    fun `search() by genre returns games successfully`()
}