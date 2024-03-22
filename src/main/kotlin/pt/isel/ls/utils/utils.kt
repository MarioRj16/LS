package pt.isel.ls.utils

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random


object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

fun currentLocalDateTime(): kotlinx.datetime.LocalDateTime = LocalDateTime.now().toKotlinLocalDateTime()

fun tomorrowLocalDateTime() = LocalDateTime.now().plusDays(1L).toKotlinLocalDateTime()

fun yesterdayLocalDateTime() = LocalDateTime.now().minusDays(1L).toKotlinLocalDateTime()

// TODO: Change this name
fun plusMillis(ms: Long) = LocalDateTime.now().plusNanos(ms * 1000000).toKotlinLocalDateTime()

fun kotlinx.datetime.LocalDateTime.isPast() = this < currentLocalDateTime()

fun <T> List<T>.paginate(skip: Int, limit: Int): List<T> {
    require(skip >= 0){"skip must be a non negative integer\nskip=$skip"}
    require(limit >= 0){"Limit must be a non negative integer\nlimit=$limit"}
    if (this.isEmpty()) return emptyList()
    val lastIndex:Int = if (limit > size) size else limit+skip
    return subList(skip, lastIndex)
}

fun kotlinx.datetime.LocalDateTime.toTimeStamp(): Timestamp = Timestamp.valueOf(toJavaLocalDateTime())
fun emailIsValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    return emailRegex.matches(email)
}

internal fun generateRandomString(): String {
    val allowedChars = ('a'..'z') + ('A'..'Z')
    return List(Random.nextInt(5, 16)) { allowedChars.random() }.joinToString("")
}

fun generateRandomEmail(): String =
    "${generateRandomString()}@${generateRandomString()}.com"