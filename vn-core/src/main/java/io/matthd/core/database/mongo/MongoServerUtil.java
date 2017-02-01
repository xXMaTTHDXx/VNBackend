package io.matthd.core.database.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import io.matthd.core.Core;
import org.bukkit.Bukkit;

/**
 * Created by Matt on 2017-01-29.
 */
public class MongoServerUtil {

    private MongoDatabase database;

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
}
