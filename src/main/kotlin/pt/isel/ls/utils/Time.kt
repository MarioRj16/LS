package pt.isel.ls.utils

import java.sql.Timestamp
import java.time.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

/**
 * Adds the specified number of days to the current date and time.
 *
 * @param days The number of days to add. Defaults to 1 if not provided.
 * @return The resulting LocalDateTime after adding the specified number of days.
 */
fun plusDaysToCurrentDateTime(days: Long = 1L): kotlinx.datetime.LocalDateTime = LocalDateTime.now().plusDays(days).toKotlinLocalDateTime()

/**
 * Adds the specified number of milliseconds to the current date and time.
 *
 * @param ms The number of milliseconds to add. Defaults to 1 if not provided.
 * @return The resulting LocalDateTime after adding the specified number of milliseconds.
 */
fun plusMillisecondsToCurrentDateTime(ms: Long = 1L): kotlinx.datetime.LocalDateTime =
    LocalDateTime.now().plusNanos(ms * 100_000_0).toKotlinLocalDateTime()

/**
 * Subtracts the specified number of days from the current date and time.
 *
 * @param days The number of days to subtract. Defaults to 1 if not provided.
 * @return The resulting LocalDateTime after subtracting the specified number of days.
 */
fun minusDaysToCurrentDateTime(days: Long = 1L): kotlinx.datetime.LocalDateTime =
    LocalDateTime.now().minusDays(days).toKotlinLocalDateTime()

/**
 * Subtracts the specified number of milliseconds from the current date and time.
 *
 * @param ms The number of milliseconds to subtract. Defaults to 1 if not provided.
 * @return The resulting LocalDateTime after subtracting the specified number of milliseconds.
 */
fun minusMillisecondsToCurrentDateTime(ms: Long = 1L): kotlinx.datetime.LocalDateTime =
    LocalDateTime.now().minusNanos(ms * 100_000_0).toKotlinLocalDateTime()

/**
 * Retrieves the current LocalDateTime.
 *
 * @return The current LocalDateTime.
 */
fun currentLocalDateTime(): kotlinx.datetime.LocalDateTime = LocalDateTime.now().toKotlinLocalDateTime()

/**
 * Checks if this LocalDateTime is in the past relative to the current date and time.
 *
 * @return True if this LocalDateTime is in the past, false otherwise.
 */
fun kotlinx.datetime.LocalDateTime.isPast(): Boolean = this < currentLocalDateTime()

/**
 * Checks if this LocalDateTime is in the future relative to the current date and time.
 *
 * @return True if this LocalDateTime is in the future, false otherwise.
 */
fun kotlinx.datetime.LocalDateTime.isFuture(): Boolean = this > currentLocalDateTime()

/**
 * Converts this LocalDateTime to a Timestamp object.
 *
 * @return The equivalent Timestamp object.
 */
fun kotlinx.datetime.LocalDateTime.toTimeStamp(): Timestamp = Timestamp.valueOf(toJavaLocalDateTime())
