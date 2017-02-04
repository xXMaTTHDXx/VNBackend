package io.matthd.core.bungee;

import io.matthd.core.Core;
import io.matthd.core.player.VNPlayer;
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
                System.out.println("Received update rank message!");
                updateRanksAndPlayers();
            }
            else {
                System.out.println(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRanksAndPlayers() {
        Core.getInstance().getGroupManager().flushAndUpdate();

        for (VNPlayer player : Core.getInstance().getPlayerManager().getOnlinePlayers()) {

            for (String perm : player.getData().getGroup().getPermissions()) {
                player.bukkitPlayer.addAttachment(Core.getInstance(), perm, true);
            }
        }
    }
}
