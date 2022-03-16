package me.clockclap.tct.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public interface ITctConfiguration {

    void init() throws IOException;

    FileConfiguration getConfig();

    FileConfiguration getChat();

    int getTime(FileConfiguration config, String value, int defaultValue);

    File getConfigFile();

    File getChatFile();

}
