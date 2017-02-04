package io.matthd.core.player;

import io.matthd.core.Core;
import io.matthd.core.database.mongo.MongoServerUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import rx.Observable;


/**
 * Created by Matt on 2017-01-29.
 */
public class Connector {

    private Core plugin;

    public Connector(Core plugin) {
        this.plugin = plugin;
        observe();
    }

    private Observable<Event> observe() {
        Observable<Event> observable = plugin.observeEvent(EventPriority.HIGHEST, PlayerJoinEvent.class, PlayerQuitEvent.class).map(playerEvent -> playerEvent);

        observable.subscribe(event -> {

            new BukkitRunnable() {
                @Override
                public void run() {
                    MongoServerUtil.setCurrentPlayers(Core.nodeName);
                }
            }.runTaskLaterAsynchronously(Core.getInstance(), 5L);

            if (event instanceof PlayerQuitEvent) {
                ((PlayerQuitEvent) event).setQuitMessage(ChatColor.RED + ChatColor.BOLD.toString() + "[Leave] " + ((PlayerQuitEvent) event).getPlayer().getName());
                plugin.getPlayerManager().logout(plugin.getPlayerManager().getPlayer(((PlayerQuitEvent) event).getPlayer()));
            }
            else if(event instanceof PlayerJoinEvent) {
                ((PlayerJoinEvent) event).setJoinMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "[Join] " + ((PlayerJoinEvent) event).getPlayer().getName());
                plugin.getPlayerManager().login(((PlayerJoinEvent) event).getPlayer());
            }
            else {

            }
        });
        return observable;
    }
}