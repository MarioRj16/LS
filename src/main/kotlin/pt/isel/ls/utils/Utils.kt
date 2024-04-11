package pt.isel.ls.utils

/**
 * Paginates a list by skipping a certain number of elements and limiting the size of the result.
 *
 * @param skip The number of elements to skip from the start of the list.
 * @param limit The maximum number of elements to include in the result.
 * @return A list containing the paginated elements.
 * @throws IllegalArgumentException If either `skip` or `limit` is negative.
 */
fun <T> List<T>.paginate(
    skip: Int,
    limit: Int,
): List<T> {
    require(skip >= 0) { "skip must be a non negative integer\nskip=$skip" }
    require(limit >= 0) { "Limit must be a non negative integer\nlimit=$limit" }
    if (this.isEmpty()) return emptyList()
    val lastIndex: Int = if (limit > size) size else limit + skip
    return subList(skip, lastIndex)
}

/**
 * Checks if an email address is valid.
 *
 * @param email The email address to check.
 * @return `true` if the email address is valid, `false` otherwise.
 */
fun emailIsValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    return emailRegex.matches(email)
}

/**
 * Checks if an integer is positive.
 *
 * @return `true` if the integer is positive, `false` otherwise.
 */
fun Int.isPositive(): Boolean {
    return this > 0
}

/**
 * Checks if an integer is not positive.
 *
 * @return `true` if the integer is not positive, `false` otherwise.
 */
fun Int.isNotPositive(): Boolean {
    return this <= 0
}

/**
 * Checks if an integer is negative.
 *
 * @return `true` if the integer is negative, `false` otherwise.
 */
fun Int.isNegative(): Boolean {
    return this < 0
}

/**
 * Checks if an integer is not negative.
 *
 * @return `true` if the integer is not negative, `false` otherwise.
 */
fun Int.isNotNegative(): Boolean {
    return this >= 0
}

/**
 * Validates an integer based on a provided function. If the integer is null, it returns a default value or throws an exception.
 *
 * @param defaultValue The value to return if the integer is null.
 * @param function The function to validate the integer.
 * @return The integer if it's valid.
 * @throws IllegalArgumentException If the integer is null and no default value is provided, or if the integer is not valid.
 */
fun Int?.validateInt(defaultValue: Int? = null, function: (Int) -> Boolean): Int {
    if (this == null) {
        if (defaultValue != null) {
            return defaultValue
        }
        throw IllegalArgumentException("Invalid argument id can't be null")
    }
    if (!function(this)) {
        throw IllegalArgumentException("Invalid argument: Int is not valid\nInt=$this")
    }
    return this
}
