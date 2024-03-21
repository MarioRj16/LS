package pt.isel.ls.utils

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.data.Storage
import pt.isel.ls.domain.Player
import pt.isel.ls.utils.exceptions.AuthorizationException
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.chrono.ChronoLocalDateTime
import java.util.*
import javax.naming.AuthenticationException


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

fun kotlinx.datetime.LocalDateTime.isPast() = this < currentLocalDateTime()

fun <T> List<T>.paginate(skip: Int, limit: Int): List<T> {
    if (this.isEmpty()) return emptyList()
    val lastIndex:Int = if (limit > size) size else limit+skip
    return subList(skip, lastIndex)
}

fun kotlinx.datetime.LocalDateTime.toTimeStamp(): Timestamp = Timestamp.valueOf(toJavaLocalDateTime())
fun emailIsValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    return emailRegex.matches(email)
}

