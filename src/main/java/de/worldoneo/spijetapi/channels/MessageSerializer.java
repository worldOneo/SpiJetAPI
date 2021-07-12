package de.worldoneo.spijetapi.channels;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


/**
 * Any message serializer must full fill that:
 * <p>
 * <code>
 * serializer.deserialize(serializer.serialize(i)).equals(i);
 * </code>
 * </p>
 * is true, otherwise it must throw an error.
 * As this is a serializer for the {@link MessagingChannel} which is an abstraction of networking,
 * there is no point to send a null.
 *
 * @param <T> the Type for the serializer to serialize
 */
public interface MessageSerializer<T> {
    /**
     * Deserializes an byte array into type T.
     * Due to the fact, that an object can either be serialized
     * or this method must throw an error, this
     * method shall not return null.
     *
     * @param message the byte array to deserialize from
     * @return a new instance of T from the byte array
     * @throws IOException if an error occurred or the message wasn't deserializable
     */
    @NotNull
    T deserialize(byte[] message) throws IOException;

    /**
     * Serializes an instance of type T into an byte array
     *
     * @param message the instance to serialized
     * @return the byte array it was serialized to
     * @throws IOException if an error occurred while serializing the message.
     */
    byte[] serialize(@NotNull T message) throws IOException;
}
