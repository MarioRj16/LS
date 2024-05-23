package pt.isel.ls.utils.factories

import java.util.*
import pt.isel.ls.api.models.players.PlayerCreate
import pt.isel.ls.data.PlayersData
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomPassword
import pt.isel.ls.utils.generateRandomString
import pt.isel.ls.utils.values.Email
import pt.isel.ls.utils.values.Password

class PlayerFactory(private val players: PlayersData? = null) {
    fun createRandomPlayer(name: String? = null, email: Email? = null, password: Password? = null): Player {
        if (players != null) {
            val playerName = name ?: generateRandomString()
            val playerEmail = email ?: generateRandomEmail()
            val playerPassword = password ?: generateRandomPassword()
            return players.create(PlayerCreate(playerName, playerEmail, playerPassword))
        } else {
            val playerId = (1..50).random()
            val playerName = generateRandomString()
            val playerEmail = generateRandomEmail()
            val playerPassword = generateRandomPassword()
            val playerToken = UUID.randomUUID()
            return Player(playerId, playerName, playerEmail, playerPassword.hash(), playerToken)
        }
    }
}
