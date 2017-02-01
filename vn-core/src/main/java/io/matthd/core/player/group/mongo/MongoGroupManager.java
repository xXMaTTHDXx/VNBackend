package io.matthd.core.player.group.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import io.matthd.core.Core;
import io.matthd.core.bungee.BungeeUtil;
import io.matthd.core.database.mongo.MongoDatabase;
import io.matthd.core.player.group.GroupManager;
import io.matthd.core.player.group.VNGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-30.
 */
public class MongoGroupManager implements GroupManager {

    private MongoDatabase database;
    private List<VNGroup> allGroups;

    public MongoGroupManager(MongoDatabase database) {
        this.database = database;
        allGroups = new ArrayList<>();
    }

    @Override
    public List<VNGroup> getAllGroups() {
        return allGroups;
    }

    @Override
    public void createGroup(VNGroup group) {
        if (groupExists(group)) return;

        this.allGroups.add(group);

        MongoGroup mongoGroup = (MongoGroup) group;

        DBObject find = new BasicDBObject("_id", mongoGroup.getRawName());

        if (this.database.getCollection("groups").find(find) != null) {
            return;
        }

        this.database.getCollection("groups").insert(mongoGroup.getDbObject());
        BungeeUtil.sendPluginMessageToAll("updateRanks");
    }

    @Override
    public void deleteGroup(VNGroup group) {
        this.allGroups.remove(group);

    }

    @Override
    public void createGroups() {
        createGroup(new MongoGroup("Default", "&7", "", new ArrayList<>(), ""));
    }

    @Override
    public void flushAndUpdate() {
        List<VNGroup> groups = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {

            DBCursor cursor = database.getCollection("groups").find();

            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                groups.add(new MongoGroup(object));
            }
        });

        this.allGroups.clear();
        this.allGroups = groups;
    }

    public String serializeChildren(VNGroup group) {
        String children = "";
        for (VNGroup child : group.getChildren()) {

            if (group.getChildren().indexOf(child) == group.getChildren().size()) {
                children += child.getRawName();
            } else {
                children += child.getRawName() + ":";
            }
        }
        return children;
    }

    @Override
    public List<VNGroup> deserializeChildren(String children) {
        List<VNGroup> groups = new ArrayList<>();

        for (String child : children.split(":")) {
            VNGroup group = getGroupByName(child);

            groups.add(group);
        }
        return groups;
    }

    @Override
    public boolean hasPermission(Player player, String node) {
        return Core.getInstance().getPlayerManager().getPlayer(player).getData().getGroup().getPermissions().hasPermission(node);
    }
}
