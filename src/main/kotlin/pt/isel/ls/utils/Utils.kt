package pt.isel.ls.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

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
fun Long.toLocalDateTime(): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant.toLocalDateTime(TimeZone.UTC)
}

fun LocalDateTime.toLong(): Long =
    this.toInstant(TimeZone.UTC).toEpochMilliseconds()
