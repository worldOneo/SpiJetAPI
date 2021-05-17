package de.worldoneo.spijetapi.channels;

import com.google.common.collect.Iterables;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public abstract class MessagingChannel<T> implements PluginMessageListener {
    @Getter
    private final String channelName;
    private final MessageSerializer<T> serializer;
    private final Plugin plugin;

    public MessagingChannel(Plugin plugin, String channelName, MessageSerializer<T> serializer) {
        this.channelName = channelName;
        this.serializer = serializer;
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    /**
     * An message was received for this channel.
     *
     * @param player  the player who send the message.
     * @param message the deserialized message which was send.
     */
    public abstract void messageReceived(Player player, T message);

    /**
     * Called if ann error occurred while receiving an message
     *
     * @param player    the player who send the message
     * @param exception the exception which was thrown
     */
    public void messageError(Player player, Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("BungeeCord"))
            return;

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
             DataInputStream dais = new DataInputStream(byteArrayInputStream)) {
            if (!dais.readUTF().equals(this.channelName)) return;

            int length = dais.readShort();
            byte[] buff = new byte[length];
            dais.readFully(buff);
            T data = this.serializer.deserialize(buff);
            this.messageReceived(player, data);
        } catch (IOException exception) {
            this.messageError(player, exception);
        }
    }

    /**
     * Forwards the message to "ALL" with the first player we can get.
     *
     * @param message the message to forward.
     * @throws IOException if no player is online to send the message or the serializer fails
     *                     throws an error
     */
    public void sendMessage(@NotNull T message) throws IOException {
        Player sender = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (sender == null)
            throw new IOException("No player online to send the message!");

        this.sendMessage(message, sender);
    }

    /**
     * Forwards a message to "ALL" with the given player as sender
     *
     * @param message The message to send.
     * @param player  The player to send the message from
     * @throws IOException if an error occurred while sending the message or serialisation failed
     */
    public void sendMessage(@NotNull T message, @NotNull Player player) throws IOException {
        this.sendMessage(message, player, "ALL");
    }

    /**
     * Forwards a message to another server with the given player as sender and the targets as argument
     *
     * @param message The message to send
     * @param player  The player to send the message from
     * @param targets The target to send the message to
     * @throws IOException if an error occurred while sending the message or serialisation failed
     */
    public void sendMessage(@NotNull T message, @NotNull Player player, @NotNull String targets) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeUTF("Forward");
        dataOutputStream.writeUTF(targets);
        dataOutputStream.writeUTF(this.channelName);
        byte[] data = this.serializer.serialize(message);
        dataOutputStream.writeShort(data.length);
        dataOutputStream.write(data);
        dataOutputStream.flush();

        player.sendPluginMessage(this.plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}
