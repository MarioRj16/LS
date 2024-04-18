package pt.isel.ls.utils

import pt.isel.ls.api.models.games.GameSearch
import kotlin.random.Random

/**
 * Generates a random email address.
 *
 * The email address is generated by concatenating a random string before the '@' symbol
 * with another random string after the '@' symbol and appending '.com' at the end.
 *
 * @return A randomly generated email address.
 */
fun generateRandomEmail(): String = "${generateRandomString()}@${generateRandomString()}.com"

/**
 * Generates a random string of characters.
 *
 * The string length is randomly chosen between 5 and 15 characters.
 * The characters in the string are selected from the English alphabet (both lowercase and uppercase).
 *
 * @return A randomly generated string.
 */
fun generateRandomString(): String {
    // Define the characters allowed in the random string
    val allowedChars = ('a'..'z') + ('A'..'Z')
    // Generate a list of random characters with a random length between 5 and 15
    return List(Random.nextInt(5, 16)) { allowedChars.random() }.joinToString("")
}

/**
 * Generates a set of random integers.
 *
 * @param size The number of integers to generate.
 * @param min The minimum possible value of the generated integers (inclusive).
 * @param max The maximum possible value of the generated integers (inclusive).
 * @return A set of randomly generated integers.
 * @throws IllegalArgumentException If `size` is negative or `min` is greater than `max`.
 */
fun generateSetOfRandomInts(size: Int, min: Int = 1, max: Int = 10): Set<Int> {
    require(size >= 0) { "Size must be a non-negative number" }
    require(min <= max) { "Minimum value must be less than or equal to the maximum value" }

    return generateSequence { Random.nextInt(min, max + 1) }
        .distinct()
        .take(size)
        .toSet()
}

/**
 * Generates a random `GameSearch` object.
 *
 * @param isEmpty If `true`, the generated `GameSearch` object will have null `developer` and an empty set of `genres`.
 * @return A randomly generated `GameSearch` object.
 */
fun generateRandomGameSearch(isEmpty: Boolean = false): GameSearch {
    if (isEmpty) {
        return GameSearch(null, emptySet())
    }

    val developer = generateRandomString()
    val genres = generateSetOfRandomInts(Random.nextInt(1, 6))

    return GameSearch(developer, genres)
}
