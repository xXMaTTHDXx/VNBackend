package io.matthd.core.player;


import io.matthd.core.Core;
import io.matthd.core.player.achievement.Achievement;
import io.matthd.core.player.group.VNGroup;
import io.matthd.core.player.statistic.Statistic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matt on 2017-01-29.
 */
public class PlayerData {
    private UUID uuid;

    private VNGroup group;
    private int tokens;
    private List<Achievement> achievements;
    private List<Statistic> statistics;

    public PlayerData(UUID uuid, VNGroup group, int tokens, List<Achievement> achievements, List<Statistic> statistics) {
        this.uuid = uuid;
        this.group = group;
        this.tokens = tokens;
        this.achievements = achievements;
        this.statistics = statistics;
    }

    public PlayerData(UUID uuid, ResultSet set) {
        this.uuid = uuid;
        try {
            this.group = Core.getInstance().getGroupManager().getGroupByName(set.getString("Group"));
            this.tokens = set.getInt("Tokens");
            this.achievements = Core.getInstance().getAchievementHandler().deserialize(set.getString("Achievements"));
            this.statistics = Core.getInstance().getStatisticHandler().deserialize(set.getString("Statistics"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public VNGroup getGroup() {
        return group;
    }

    public void setGroup(VNGroup group) {
        this.group = group;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }
}
