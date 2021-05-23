package me.clockclap.tct.plugin;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.game.TctGame;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TctPluginLoader {

    private Plugin[] loadedPlugins;
    private final Class<?> clazz;

    public TctPluginLoader(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Plugin[] getLoadedPlugins() {
        return loadedPlugins;
    }

    public void loadPlugins(String directory) {
        Bukkit.getPluginManager().registerInterface(JavaPluginLoader.class);

        File pluginFolder = new File(directory);

        if (pluginFolder.exists()) {
            Plugin[] plugins = Bukkit.getPluginManager().loadPlugins(pluginFolder);
            List<Plugin> pluginList = new ArrayList<>();
            for (Plugin plugin : plugins) {
                try {
                    if(plugin instanceof TctJavaPlugin) {
                        String message = String.format("Loading %s", plugin.getDescription().getFullName());
                        plugin.getLogger().info(message);
                        plugin.onLoad();
                        pluginList.add(plugin);
                    } else {
                        String message = "Cannot loaded " + plugin.getDescription().getFullName() + " because plugin is not tct plugin.";
                        Logger.getLogger(clazz.getName()).log(Level.SEVERE, message);
                    }
                } catch (Throwable ex) {
                    Logger.getLogger(clazz.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
                }
            }
            loadedPlugins = pluginList.toArray(new Plugin[0]);
        } else {
            pluginFolder.mkdir();
        }
    }

    public final void enablePlugins() {
        SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();
        for(Plugin pl : loadedPlugins) {
            try {
                TctJavaPlugin jPlugin = (TctJavaPlugin) pl;
                jPlugin.setGame(NanamiTctApi.game);

                List<Permission> perms = pl.getDescription().getPermissions();

                for (Permission perm : perms) {
                    try {
                        pluginManager.addPermission(perm, false);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(clazz.getName()).log(Level.WARNING, "Plugin " + pl.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
                    }
                }
                pluginManager.dirtyPermissibles();

                pluginManager.enablePlugin(pl);
            } catch (Throwable ex) {
                Logger.getLogger(clazz.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + pl.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
        }
    }

}
