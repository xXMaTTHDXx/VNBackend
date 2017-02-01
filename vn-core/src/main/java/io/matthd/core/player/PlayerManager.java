package io.matthd.core.player;

import io.matthd.core.Core;
import io.matthd.core.database.sql.SQLDatabase;
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

    private List<VNPlayer> allPlayers = new ArrayList<>();

    public VNPlayer getPlayer(Player player) {
        for (VNPlayer gamePlayer : allPlayers) {
            if (gamePlayer.getBukkitPlayer().getName().equalsIgnoreCase(player.getName())) {
                return gamePlayer;
            }
        }
        return null;
    }
    private SQLDatabase database;

    public PlayerManager(SQLDatabase database) {
        this.database = database;
    }

    public void login(Player player) {
        final ResultSet[] result = {null};
        final PlayerData[] data = {null};

        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            PreparedStatement statement = null;
            try {
                statement = database.getConnection().prepareStatement("SELECT * FROM `users` WHERE Uuid=?");
                statement.setString(1, player.getUniqueId().toString());

                result[0] = statement.executeQuery();

                if (result[0].next()) {
                    data[0] = new PlayerData(player.getUniqueId(), result[0]);
                } else {
                    data[0] = new PlayerData(player.getUniqueId(), Core.getInstance().getGroupManager().getGroupByName("Default"), 0, new ArrayList<>(), new ArrayList<>());
                    PreparedStatement set = database.getConnection().prepareStatement("INSERT INTO `users` (Uuid, Rank, Tokens, Statistics, Achievements) VALUES(?,?,?,?,?)");
                    set.setString(1, player.getUniqueId().toString());
                    set.setString(2, "Default");
                    set.setInt(3, 0);
                    set.setString(4, null);
                    set.setString(5, null);
                    set.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        allPlayers.add(new VNPlayer(player, data[0]));
    }

    public void logout(VNPlayer gamePlayer) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            try {
                PreparedStatement statement = database.getConnection().prepareStatement("UPDATE `users` SET Rank=?, Tokens=?, Statistics=?, Achievements=? WHERE Uuid=?");
                statement.setString(1, gamePlayer.getData().getGroup().getRawName());
                statement.setInt(2, gamePlayer.getData().getTokens());
                statement.setString(3, Core.getInstance().getStatisticHandler().serialize(gamePlayer.getData().getStatistics()));
                statement.setString(4, Core.getInstance().getAchievementHandler().serialize(gamePlayer.getData().getAchievements()));
                statement.setString(5, gamePlayer.getUuid().toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        allPlayers.remove(gamePlayer);
    }
}
