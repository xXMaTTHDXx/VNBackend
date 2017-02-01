package io.matthd.bungee;

import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by Matt on 2017-01-31.
 */
public class VNBungee extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("Velocity Bungee Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Velocity Bungee Disabled");
    }
}
