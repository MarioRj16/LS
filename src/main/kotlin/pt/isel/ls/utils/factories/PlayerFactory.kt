package pt.isel.ls.utils.factories

import pt.isel.ls.data.PlayerStorage
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomString

class PlayerFactory(private val players: PlayerStorage) {
    fun createRandomPlayer(): Player {
        val randomName = generateRandomString()
        val randomEmail = generateRandomEmail()
        return players.create(randomName, randomEmail)
    }

}