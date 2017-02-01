package io.matthd.core;


import io.matthd.core.database.mongo.MongoDatabase;
import io.matthd.core.database.sql.SQLDatabase;
import io.matthd.core.player.PlayerManager;
import io.matthd.core.player.achievement.AchievementHandler;
import io.matthd.core.player.group.GroupManager;
import io.matthd.core.player.group.mongo.MongoGroupManager;
import io.matthd.core.player.statistic.StatisticHandler;
import tech.rayline.core.plugin.RedemptivePlugin;

/**
 * Created by Matt on 2017-01-29.
 */
public class Core extends RedemptivePlugin {


    private static Core instance;

    private AchievementHandler achievementHandler;
    private StatisticHandler statisticHandler;
    private PlayerManager playerManager;

    private GroupManager groupManager;
    private MongoDatabase mongoDatabase;

    @Override
    protected void onModuleEnable() throws Exception {
        instance = this;

        SQLDatabase database = new SQLDatabase("", "", "", "", "");

        this.mongoDatabase = new MongoDatabase("", 1);

        this.groupManager = new MongoGroupManager(mongoDatabase);
        this.achievementHandler = new AchievementHandler(this);
        this.statisticHandler = new StatisticHandler(this);
        this.playerManager = new PlayerManager(database);
    }

    @Override
    protected void onModuleDisable() throws Exception {
        instance = null;
    }

    public static Core getInstance() {
        return instance;
    }

    public AchievementHandler getAchievementHandler() {
        return achievementHandler;
    }

    public StatisticHandler getStatisticHandler() {
        return statisticHandler;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
