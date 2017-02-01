package io.matthd.core.bungee;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Created by Matt on 2017-01-31.
 */
public class BungeeMessageListener implements PluginMessageListener {


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("Core")) return;

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        try {
            String subChannel = in.readUTF();
            short length = in.readShort();

            byte[] msgbytes = new byte[length];
            in.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            String command = msgin.readUTF();

            if (command.equalsIgnoreCase("updateRanks")) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
