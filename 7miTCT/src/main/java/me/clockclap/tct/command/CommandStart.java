package me.clockclap.tct.command;

import com.google.common.base.Charsets;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.Game;
import org.bukkit.Sound;
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
            boolean success = plugin.getGame().preStart(p.getLocation());
            if(success == false) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PLAYERS_NEEDED);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.5F, 0.5F);
            }
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLAYER_ONLY);
        return true;
    }

}
