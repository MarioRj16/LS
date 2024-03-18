package pt.isel.ls.utils

import pt.isel.ls.data.PlayerStorage
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player

class PlayerFactory(private val players: PlayerStorage) {
        fun createRandomPlayer(): Player {
            val randomName = generateRandomString()
            val randomEmail = generateRandomEmail()
            return players.create(randomName, randomEmail)
        }
        private fun generateRandomEmail(): String =
            "${generateRandomString()}@${generateRandomString()}.com"
}