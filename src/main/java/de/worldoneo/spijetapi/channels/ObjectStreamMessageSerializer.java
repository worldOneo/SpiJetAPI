package de.worldoneo.spijetapi.channels;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ObjectStreamMessageSerializer<T extends Serializable> implements MessageSerializer<T> {
    @Override
    @SuppressWarnings("unchecked") // Unchecked cast due to ObjectInputStream
    @NotNull
    public T deserialize(byte[] message) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Object type mismatch", e);
        }
    }

    @Override
    public byte[] serialize(@NotNull T message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
