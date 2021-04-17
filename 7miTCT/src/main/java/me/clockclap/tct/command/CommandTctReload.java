package me.clockclap.tct.command;

import com.google.common.base.Charsets;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.profile.TctPlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CommandTctReload implements CommandExecutor {

    private NanamiTct plugin;

    public CommandTctReload(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p != null) {
                TctPlayerProfile profile = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).getProfile();
                boolean isAdmin = profile.isAdmin();
//              if(plugin.getTctConfig().getConfig().getStringList("admin").contains("op")) {
//                  if(p.isOp()) {
//                      isAdmin = true;
//                  }
//              }
//              if(isAdmin == false) {
//                 for (String str : plugin.getTctConfig().getConfig().getStringList("admin")) {
//                      if (NanamiTct.utilities.resetColor(p.getName()).equalsIgnoreCase(str)) {
//                          isAdmin = true;
//                          break;
//                      }
//                  }
//              }
                if (isAdmin == false) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
                    return true;
                }
                reload();
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_RELOAD_COMPLETE);
            }
            return true;
        }
        reload();
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_RELOAD_COMPLETE);
        return true;
    }

    private void reload() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(plugin.getTctConfig().getConfigFile());

        final InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            plugin.getTctConfig().getConfig().load(plugin.getTctConfig().getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p != null) {
                String name = NanamiTct.utilities.resetColor(p.getName());
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(name);
                if(data == null) {
                    continue;
                }
                boolean isAdmin = false;
                FileConfiguration config = plugin.getTctConfig().getConfig();
                if(config.getStringList("admin").contains(name)) {
                    isAdmin = true;
                } else if(config.getStringList("admin").contains("op") && p.isOp()) {
                    isAdmin = true;
                }
                data.getProfile().modify().setBoolean("admin", isAdmin).save();
            }
        }
    }

}
