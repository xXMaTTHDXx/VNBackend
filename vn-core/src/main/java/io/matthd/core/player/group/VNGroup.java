package io.matthd.core.player.group;

import java.util.List;

/**
 * Created by Matt on 2017-01-30.
 */
public interface VNGroup {

    String getRawName();

    String getPrefix();

    String getSuffix();

    void setRawName(String rawName);

    void setPrefix(String prefix);

    void setSuffix(String suffix);

    List<VNGroup> getChildren();

    void addChild(VNGroup group);

    void delChild(VNGroup group);

    PermissionSet getPermissions();

    default PermissionSet getSubPermissions() {
        PermissionSet set = new PermissionSet();
        for (VNGroup child : getChildren()) {
            set.combine(child.getPermissions());
        }
        return set;
    }

    void update();
    boolean hasPermission(String node);

    void addPermission(String permissionNode);

    void removePermission(String permissionNode);
}
