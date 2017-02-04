package io.matthd.core.database.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import io.matthd.core.Core;
import org.bukkit.Bukkit;

/**
 * Created by Matt on 2017-01-29.
 */
public class MongoServerUtil {

    private static MongoDatabase database;

    public MongoServerUtil(MongoDatabase database) {
        this.database = database;
    }

    public void connect() {
        DBCollection collection = database.getCollection("servers");

        DBObject query = new BasicDBObject("_id", Core.getInstance().getConfig().getString("servername"));

        DBObject object = new BasicDBObject("_id", Core.getInstance().getConfig().getString("servername"))
                .append("playerCount", Bukkit.getOnlinePlayers().size()).append("maxPlayers", Bukkit.getMaxPlayers());

        if (collection.find(query).one() != null) {
            collection.update(query, object);
        }
        else {
            collection.insert(object);
        }
    }

    public static int getCurrentPlayers(String serverName) {
        DBObject query = new BasicDBObject("_id", serverName);

        DBCursor cursor = Core.getInstance().getMongoDatabase().getCollection("servers").find(query);

        return (int) cursor.one().get("playerCount");
    }

    public static int getMaxPlayers(String serverName) {
        DBObject query = new BasicDBObject("_id", serverName);

        DBCursor cursor = Core.getInstance().getMongoDatabase().getCollection("servers").find(query);

        return (int) cursor.one().get("maxPlayers");
    }

    public static void setCurrentPlayers(String serverName) {
        DBObject find = new BasicDBObject("_id", serverName);

        DBObject toReplace = new BasicDBObject("_id", serverName)
                .append("playerCount", Bukkit.getOnlinePlayers().size()).append("maxPlayers", find.get("maxPlayers"));

        database.getCollection("servers").update(find, toReplace);

    }
}
