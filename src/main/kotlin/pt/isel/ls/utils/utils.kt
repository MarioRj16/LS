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

object UUIDSerializer: KSerializer<UUID>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

fun currentLocalDateTime() = LocalDateTime.now().toKotlinLocalDateTime()

fun <K, V> Map<K, V>.filterValuesNotNull() = mapNotNull { (k, v) -> v?.let { k to v } }.toMap()

fun <T> Collection<T>.getSublistLastIdx(skip: Int, limit: Int): Int {
    return if((skip + limit) > size) size-skip-1 else skip+limit-1
}