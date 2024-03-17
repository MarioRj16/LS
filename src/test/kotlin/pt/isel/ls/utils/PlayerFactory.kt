package pt.isel.ls.utils

import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Player

internal open class PlayerFactory(private val db: DataMem) {
        fun createRandomPlayer(): Player {
            val randomName = generateRandomString()
            val randomEmail = generateRandomEmail()
            return db.players.create(randomName, randomEmail)
        }
        private fun generateRandomEmail(): String =
            "${generateRandomString()}@${generateRandomString()}.com"
}