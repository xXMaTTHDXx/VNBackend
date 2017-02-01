package io.matthd.core.player.group.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import io.matthd.core.Core;
import io.matthd.core.bungee.BungeeUtil;
import io.matthd.core.player.group.PermissionSet;
import io.matthd.core.player.group.VNGroup;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-30.
 */
public class MongoGroup implements VNGroup {

    private List<VNGroup> children;
    private PermissionSet permissions;

    private String rawName;
    private String prefix, suffix;

    private BasicDBObject dbObject;

    public MongoGroup(DBObject object) {
        //Loading from db
        this.dbObject = (BasicDBObject) object;

        this.rawName = (String) dbObject.get("_id");
        this.prefix = dbObject.getString("prefix");
        this.suffix = dbObject.getString("suffix");
        String[] perms = dbObject.getString("permissions").split(":");
        this.permissions = new PermissionSet(perms);
        this.children = Core.getInstance().getGroupManager().deserializeChildren(dbObject.getString("children"));
    }

    public MongoGroup(String name, String prefix, String suffix, List<String> children, String... permissions) {
        this.rawName = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.permissions = new PermissionSet(permissions);
        this.children = new ArrayList<>();

        for (String child : children) {
            VNGroup group = Core.getInstance().getGroupManager().getGroupByName(child);
            this.children.add(group);
        }

        this.dbObject = new BasicDBObject("_id", name)
                .append("prefix", prefix)
                .append("suffix", suffix)
                .append("children", Core.getInstance().getGroupManager().serializeChildren(this))
                .append("permissions", permissions);
    }

    public BasicDBObject getDbObject() {
        return dbObject;
    }

    @Override
    public String getRawName() {
        return rawName;
    }

    @Override
    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    @Override
    public String getSuffix() {
        return ChatColor.translateAlternateColorCodes('&', suffix);
    }

    @Override
    public void setRawName(String rawName) {
        this.rawName = rawName;
        this.dbObject.put("name", rawName);
        update();
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.dbObject.put("prefix", prefix);
        update();
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
        this.dbObject.put("suffix", suffix);
        update();
    }

    @Override
    public List<VNGroup> getChildren() {
        return children;
    }

    @Override
    public void addChild(VNGroup group) {
        if (this.children.contains(group)) return;
        this.children.add(group);
        this.dbObject.put("children", Core.getInstance().getGroupManager().serializeChildren(this));
        update();
    }

    @Override
    public void delChild(VNGroup group) {
        //TODO
    }

    @Override
    public PermissionSet getPermissions() {
        return permissions;
    }

    @Override
    public void update() {
        DBObject query = new BasicDBObject("_id", rawName);
        Core.getInstance().getMongoDatabase().getCollection("groups").update(query, this.dbObject);
        BungeeUtil.sendPluginMessageToAll("updateRanks");
    }

    @Override
    public boolean hasPermission(String node) {
        return this.permissions.hasPermission(node) || this.getSubPermissions().hasPermission(node);
    }

    @Override
    public void addPermission(String permissionNode) {
        if (this.permissions.contains(permissionNode)) return;

        this.permissions.add(permissionNode);
        String nodes = "";

        for (String node : permissions) {
            nodes+=node + ":";
        }

        this.dbObject.put("permissions", nodes);
    }

    @Override
    public void removePermission(String permissionNode) {

    }
}
