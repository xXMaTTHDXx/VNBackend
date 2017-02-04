package io.matthd.core.player;

import org.bukkit.entity.Player;

/**
 * Created by Matt on 2017-01-29.
 */
public class VNPlayer {
    public Player bukkitPlayer;
    public PlayerData meta;


    public VNPlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public void setBukkitPlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public PlayerData getData() {
        return meta;
    }

    public void setMeta(PlayerData meta) {
        this.meta = meta;
    }
}