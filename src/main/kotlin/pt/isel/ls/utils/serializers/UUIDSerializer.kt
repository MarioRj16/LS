package pt.isel.ls.utils.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

/**
 * Serializer for UUID objects.
 *
 * This serializer is used to serialize and deserialize UUID objects.
 * It implements the [KSerializer] interface.
 *
 * @property descriptor The descriptor for the serializer, representing the UUID as a string.
 */
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    /**
     * Deserializes a UUID from the given decoder.
     *
     * @param decoder The decoder to use for deserialization.
     * @return The deserialized UUID.
     */
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    /**
     * Serializes the given UUID using the provided encoder.
     *
     * @param encoder The encoder to use for serialization.
     * @param value The UUID to serialize.
     */
    override fun serialize(
        encoder: Encoder,
        value: UUID,
    ) {
        encoder.encodeString(value.toString())
    }
}
