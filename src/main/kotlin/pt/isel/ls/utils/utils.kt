package pt.isel.ls.utils

import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

fun currentLocalDateTime() = LocalDateTime.now().toKotlinLocalDateTime()

fun <T> List<T>.paginate(skip: Int, limit: Int): List<T> {
    var firstIndex: Int = if (skip > size) size else skip
    firstIndex--
    var lastIndex: Int = if (size > skip + limit) skip + limit else size
    lastIndex--
    return subList(firstIndex, lastIndex)
}

