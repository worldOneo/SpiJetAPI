package de.worldoneo.spijetapi.channels;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ObjectStreamMessageSerializer<T extends Serializable> implements MessageSerializer<T> {
    @Override
    @SuppressWarnings("unchecked") // Unchecked cast due to ObjectInputStream
    @NotNull
    public T deserialize(byte[] message) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(message);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Object type mismatch", e);
        }
    }

    @Override
    public byte[] serialize(@NotNull T message) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(message);
        oos.flush();
        return out.toByteArray();
    }
}
