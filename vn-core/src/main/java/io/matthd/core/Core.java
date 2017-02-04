package io.matthd.core;


import io.matthd.core.bungee.BungeeMessageListener;
import io.matthd.core.database.mongo.MongoDatabase;
import io.matthd.core.database.mongo.MongoServerUtil;
import io.matthd.core.database.sql.SQLDatabase;
import io.matthd.core.listeners.ChatListener;
import io.matthd.core.player.Connector;
import io.matthd.core.player.PlayerManager;
import io.matthd.core.player.achievement.AchievementHandler;
import io.matthd.core.player.group.GroupManager;
import io.matthd.core.player.group.commands.GroupCommand;
import io.matthd.core.player.group.mongo.MongoGroupManager;
import io.matthd.core.player.statistic.StatisticHandler;
import net.milkbowl.vault.permission.Permission;
import tech.rayline.core.plugin.RedemptivePlugin;

/**
 * Created by Matt on 2017-01-29.
 */
public class Core extends RedemptivePlugin {


    private static Core instance;

    private AchievementHandler achievementHandler;
    private StatisticHandler statisticHandler;
    private PlayerManager playerManager;

    private MongoGroupManager groupManager;
    private MongoDatabase mongoDatabase;
    private SQLDatabase sqlDatabase;

    public static String nodeName;

    private Permission perms;

    @Override
    protected void onModuleEnable() throws Exception {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        nodeName = getConfig().getString("servername");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Core");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Core", new BungeeMessageListener());

        sqlDatabase = new SQLDatabase("localhost", "3306", "", "", "");
        sqlDatabase.connect();

        this.mongoDatabase = new MongoDatabase("localhost", 27017);
        this.mongoDatabase.connect();


        new MongoServerUtil(mongoDatabase).connect();

        this.groupManager = new MongoGroupManager(mongoDatabase);
        this.achievementHandler = new AchievementHandler();
        this.statisticHandler = new StatisticHandler();
        this.playerManager = new PlayerManager(this);

        new Connector(this);

        this.groupManager.connect();

        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        registerCommand(new GroupCommand());
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

    public SQLDatabase getSqlDatabase() {
        return sqlDatabase;
    }
}
