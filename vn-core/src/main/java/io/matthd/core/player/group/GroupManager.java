package io.matthd.core.player.group;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Matt on 2017-01-30.
 */
public interface GroupManager {

    List<VNGroup> getAllGroups();

    default VNGroup getGroupByName(String name) {
        for (VNGroup group : getAllGroups()) {
            if (group.getRawName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    default boolean groupExists(VNGroup group) {
        return getGroupByName(group.getRawName()) != null;
    }

    void createGroup(VNGroup group);
    void deleteGroup(VNGroup group);
    void createGroups();

    void flushAndUpdate();

    String serializeChildren(VNGroup group);
    List<VNGroup> deserializeChildren(String children);

    boolean hasPermission(Player player, String node);
}
