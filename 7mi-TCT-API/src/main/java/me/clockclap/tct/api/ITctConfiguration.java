package me.clockclap.tct.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public interface ITctConfiguration {

    public void init() throws IOException;

    public FileConfiguration getConfig();

    public FileConfiguration getChat();

    public File getConfigFile();

    public File getChatFile();

}
