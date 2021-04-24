package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTct;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class TctConfiguration {

    private NanamiTct plugin;
    private FileConfiguration config;
    private File configFile;
    private FileConfiguration chat;
    private File chatFile;

    public TctConfiguration(NanamiTct plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        String datafolder = "plugins/" + plugin.getName();

        //Make the data folder
        File f = new File(datafolder);
        if(!f.exists()) {
            f.mkdir();
        }

        //Set configurations
        //config.yml
        File fConfig = new File(datafolder + "/config.yml");
        this.configFile = fConfig;
        boolean existsConfig = true;
        if(!fConfig.exists()) {
            fConfig.createNewFile();
            existsConfig = false;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(fConfig);
        if(!existsConfig) {
            try {
                InputStream inputStream = plugin.getResource("config.yml");
                File file = fConfig;
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte['?'];
                int length;
                while ((length = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.config = config;

        //chat.yml
        File fChat = new File(datafolder + "/chat.yml");
        this.chatFile = fChat;
        boolean existsChat = true;
        if(!fChat.exists()) {
            fChat.createNewFile();
            existsChat = false;
        }
        FileConfiguration chat = YamlConfiguration.loadConfiguration(fChat);
        if(!existsChat) {
            try {
                InputStream inputStream = plugin.getResource("chat.yml");
                File file = fChat;
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte['?'];
                int length;
                while ((length = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.chat = chat;

    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public FileConfiguration getChat() {
        return this.chat;
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public File getChatFile() {
        return this.chatFile;
    }

}