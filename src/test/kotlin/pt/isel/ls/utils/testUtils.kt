package pt.isel.ls.utils

import kotlin.random.Random

internal fun generateRandomString(): String {
    val allowedChars = ('a'..'z') + ('A'..'Z')
    return List(Random.nextInt(5, 16)) { allowedChars.random() }.joinToString("")
}

fun generateRandomEmail(): String =
    "${generateRandomString()}@${generateRandomString()}.com"