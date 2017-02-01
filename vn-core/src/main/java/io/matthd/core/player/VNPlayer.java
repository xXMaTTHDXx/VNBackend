package io.matthd.core.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Matt on 2017-01-29.
 */
public class VNPlayer {
    private String name;
    private Player bukkitPlayer;

    private UUID uuid;

    private PlayerData data;

    public VNPlayer(Player bukkitPlayer, PlayerData data) {
        this.bukkitPlayer = bukkitPlayer;
        this.uuid = bukkitPlayer.getUniqueId();
        this.name = bukkitPlayer.getName();
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PlayerData getData() {
        return data;
    }

    public void sendMessage(String msg) {
        bukkitPlayer.sendMessage(msg);
    }

    public void teleport(Location loc) {
        bukkitPlayer.teleport(loc);
    }
}
