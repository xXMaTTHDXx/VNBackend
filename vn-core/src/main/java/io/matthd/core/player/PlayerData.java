package io.matthd.core.player;


import io.matthd.core.Core;
import io.matthd.core.player.achievement.Achievement;
import io.matthd.core.player.group.VNGroup;
import io.matthd.core.player.statistic.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-29.
 */
public class PlayerData {
    private int coins;
    private List<Achievement> achievements;
    private VNGroup rank;
    private List<Statistic> stats;
    private Player bukkitPlayer;

    private PermissionAttachment attachment;

    public PlayerData(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.coins = 0;
        this.achievements = new ArrayList<>();
        this.stats = new ArrayList<>();
        this.rank = Core.getInstance().getGroupManager().getGroupByName("Default");
        this.attachment = bukkitPlayer.addAttachment(Core.getInstance());
    }

    private PlayerData(Player bukkitPlayer, int coins, List<Achievement> achievements, VNGroup rank, List<Statistic> stats) {
        this.bukkitPlayer = bukkitPlayer;
        this.coins = coins;
        this.achievements = achievements;
        this.rank = rank;
        this.stats = stats;
        this.attachment = bukkitPlayer.addAttachment(Core.getInstance());
    }

    public PlayerData(Player bukkitPlayer, ResultSet set) throws Exception {
        this.bukkitPlayer = bukkitPlayer;
        this.achievements = deserializeAchievements(set.getString("Achievements"));
        this.coins = set.getInt("Coins");
        this.rank = Core.getInstance().getGroupManager().getGroupByName(set.getString("Rank"));
        this.stats = deserializeStatistics(set.getString("Statistics"));
        this.attachment = bukkitPlayer.addAttachment(Core.getInstance());
    }

    private List<Achievement> deserializeAchievements(String serialized) {
        List<Achievement> local = new ArrayList<>();
        for (String s : serialized.split(",")) {
            Achievement a = Core.getInstance().getAchievementHandler().getFromString(s);
            local.add(a);
        }
        return local;
    }

    private List<Statistic> deserializeStatistics(String serialized) {
        List<Statistic> local = new ArrayList<>();
        for (String s : serialized.split(",")) {
            Statistic a = Core.getInstance().getStatisticHandler().getFromString(s);
            local.add(a);
        }
        return local;
    }

    public int getCoins() {
        return coins;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public VNGroup getGroup() {
        return rank;
    }

    public void setGroup(VNGroup rank) {
        this.rank = rank;
    }

    public List<Statistic> getStats() {
        return stats;
    }

    public void setStats(List<Statistic> stats) {
        this.stats = stats;
    }

    public PermissionAttachment getAttachment() {
        return attachment;
    }
}
