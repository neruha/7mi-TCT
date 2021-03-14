package me.clockclap.tct;

import com.google.common.collect.UnmodifiableListIterator;
import me.clockclap.tct.api.Utilities;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NanamiTctApi extends JavaPlugin {

    public static NanamiTctApi plugin;
    public static Utilities utilities;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        utilities = new Utilities(this);

        getLogger().info("Starting up api...");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down api...");
    }
}
