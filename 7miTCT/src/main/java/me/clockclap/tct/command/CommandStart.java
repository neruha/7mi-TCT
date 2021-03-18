package me.clockclap.tct.command;

import com.google.common.base.Charsets;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
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

public class CommandStart implements CommandExecutor {

    private NanamiTct plugin;

    public CommandStart(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            boolean isAdmin = false;
            if(plugin.getTctConfig().getConfig().getStringList("admin").contains("op")) {
                if(p.isOp()) {
                    isAdmin = true;
                }
            }
            if(isAdmin == false) {
                for (String str : plugin.getTctConfig().getConfig().getStringList("admin")) {
                    if (p.getName().equalsIgnoreCase(str)) {
                        isAdmin = true;
                        break;
                    }
                }
            }
            if(isAdmin == false) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
                return true;
            }
            plugin.getGame().preStart(p.getLocation());
            return true;
        }
        return true;
    }

}
