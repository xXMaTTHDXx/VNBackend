package io.matthd.core.player;

import io.matthd.core.Core;
import io.matthd.core.events.NetworkJoinEvent;
import io.matthd.core.player.achievement.Achievement;
import io.matthd.core.player.statistic.Statistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-29.
 */
public class PlayerManager {

    private List<VNPlayer> onlinePlayers;
    private Core plugin;

    public PlayerManager(Core plugin) {
        this.plugin = plugin;
        onlinePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(this::login);

    }

    public void login(Player player) {
        VNPlayer cPlayer = new VNPlayer(player);
        ResultSet result = null;
        try {
            PreparedStatement st = plugin.getSqlDatabase().getConnection().prepareStatement("SELECT * FROM `users` WHERE Uuid=?");
            st.setString(1, player.getUniqueId().toString());
            result = st.executeQuery();

            PlayerData meta = null;
            if (result.next()) {
                meta = new PlayerData(player, result);
            } else {
                meta = new PlayerData(player);
                try {
                    PreparedStatement statement = plugin.getSqlDatabase().getConnection().prepareStatement("INSERT INTO `users` (Uuid, Coins, Achievements, Rank, Statistics) VALUES (?,?,?,?,?)");
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setInt(2, 0);
                    statement.setString(3, null);
                    statement.setString(4, "Default");
                    statement.setString(5, null);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            cPlayer.setMeta(meta);
            onlinePlayers.add(cPlayer);
            plugin.getServer().getPluginManager().callEvent(new NetworkJoinEvent(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(VNPlayer player) {
        int coins = player.getData().getCoins();
        List<Achievement> achievements = player.getData().getAchievements();
        List<Statistic> statistics = player.getData().getStats();

        StringBuilder sb = new StringBuilder();

        for (Achievement achievement : achievements) {
            sb.append(achievement.getName()).append(",");
        }

        StringBuilder statBuilder = new StringBuilder();

        for (Statistic s : statistics) {
            if (s == null || s.getName().trim().equalsIgnoreCase("")) {
                continue;
            } else {
                statBuilder.append(s.getName()).append(",");
            }
        }


        try {
            PreparedStatement statement = plugin.getSqlDatabase().getConnection().prepareStatement("UPDATE `users` SET Coins=?, Achievements=?, Rank=?, Statistics=? WHERE Uuid=?");
            statement.setInt(1, coins);
            statement.setString(2, sb.toString());
            statement.setString(3, player.getData().getGroup().getRawName());
            statement.setString(4, statBuilder.toString());
            statement.setString(5, player.getBukkitPlayer().getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onlinePlayers.remove(player);
    }

    public VNPlayer getPlayer(Player player) {
        for (VNPlayer cPlayer : onlinePlayers) {
            if (cPlayer.getBukkitPlayer().getName().equalsIgnoreCase(player.getName())) {
                return cPlayer;
            }
        }
        return null;
    }

    public List<VNPlayer> getOnlinePlayers() {
        return onlinePlayers;
    }
}
