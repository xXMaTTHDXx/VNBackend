package io.matthd.core.player.group;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matt on 2017-01-30.
 */
public class PermissionSet extends ArrayList<String> {

    public PermissionSet(String... perms) {
        addAll(Arrays.asList(perms));
    }

    public boolean hasPermission(String node) {
        return this.contains(node);
    }

    public void combine(PermissionSet set) {
        for (String node : set) {
            if (this.contains(node)) {
                continue;
            }
            else {
                this.add(node);
            }
        }
    }
}
