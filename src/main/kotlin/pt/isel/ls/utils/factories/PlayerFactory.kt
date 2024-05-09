package pt.isel.ls.utils.factories

import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString
import java.util.*

class PlayerFactory(private val players: PlayersData?) {
    fun createRandomPlayer(name: String? = null, email: Email? = null): Player {
        if(players != null) {
            val playerName = name ?: generateRandomString()
            val playerEmail = email ?: generateRandomEmail()
            return players.create(PlayerCreate(playerName, playerEmail))
        }
        else{
            val playerId= (1..50).random()
            val playerName = generateRandomString()
            val playerEmail = generateRandomEmail()
            val playerToken = UUID.randomUUID()
            return Player(playerId, playerName, playerEmail, playerToken)
        }
    }
}
