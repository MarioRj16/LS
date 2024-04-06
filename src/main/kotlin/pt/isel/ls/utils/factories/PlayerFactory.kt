package pt.isel.ls.utils.factories

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString

class PlayerFactory(private val players: PlayersData) {
    fun createRandomPlayer(): Player {
        return players.create(PlayerCreate(generateRandomString(), generateRandomEmail()))
    }
}
