package me.clockclap.tct.api;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Utilities {

    public JavaPlugin plugin;

    public Utilities(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addCommand(String command, CommandExecutor executor) {
        plugin.getCommand(command).setExecutor(executor);
    }

}
