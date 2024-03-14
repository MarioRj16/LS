package pt.isel.ls.utils

import pt.isel.ls.Data.Mem.DataMem
import pt.isel.ls.Domain.Player

internal class PlayerFactory {
    companion object: DataMem() {

        fun createRandomPlayer(): Player {
            val randomName = generateRandomString()
            val randomEmail = generateRandomEmail()
            return players.create(randomName, randomEmail)
        }
        private fun generateRandomEmail(): String =
            "${generateRandomString()}@${generateRandomString()}.com"

    }
}