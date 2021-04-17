package me.clockclap.tct.command;

import com.google.common.base.Charsets;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.profile.TctPlayerProfile;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.GameTeams;
import org.bukkit.Bukkit;
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

public class CommandStopGame implements CommandExecutor {

    private NanamiTct plugin;

    public CommandStopGame(NanamiTct plugin) {
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
                if (plugin.getGame().getReference().getGameState() == GameState.GAMING || plugin.getGame().getReference().getGameState() == GameState.STARTING) {
                    process();
                    return true;
                }
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_GAME_NOT_STARTED);
            }
            return true;
        }
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING || plugin.getGame().getReference().getGameState() == GameState.STARTING) {
            process();
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_GAME_NOT_STARTED);
        return true;
    }

    private void process() {
        if(plugin.getGame().getReference().getGameState() == GameState.STARTING) {
            plugin.getGame().getPreTimer().cancel();
        } else if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            plugin.getGame().getTimer().cancel();
        }
        plugin.getGame().stop(GameTeams.NONE);
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_STOPPED_GAME);
    }

}
