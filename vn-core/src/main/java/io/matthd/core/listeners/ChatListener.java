package io.matthd.core.listeners;

import io.matthd.core.Core;
import io.matthd.core.player.VNPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Matt on 2017-02-02.
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player pl = e.getPlayer();
        VNPlayer player = Core.getInstance().getPlayerManager().getPlayer(pl);

        String prefix = player.getData().getGroup().getPrefix();

        e.setFormat(prefix + " " + pl.getName() + ChatColor.GRAY + " >> " + ChatColor.WHITE + e.getMessage());
    }
}
