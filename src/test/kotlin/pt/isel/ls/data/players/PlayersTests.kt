package pt.isel.ls.data.players

import org.junit.jupiter.api.Test

interface PlayersTests {
    fun `create() creates player successfully`()

    fun `get() returns player successfully`()

    fun `get() returns null for non existing player`()

    fun `search() returns players successfully`()

    fun `search() by name returns players successfully`()

    fun `search() by partial name returns players successfully`()
}