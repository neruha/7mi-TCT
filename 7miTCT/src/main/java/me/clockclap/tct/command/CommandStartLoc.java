package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.profile.TctPlayerProfile;
import me.clockclap.tct.game.role.GameTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStartLoc implements CommandExecutor {

    private NanamiTct plugin;
    private String usage = ChatColor.RED + "Usage: /startloc <world> <x> <y> <z>";

    public CommandStartLoc(NanamiTct plugin) {
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
                if(plugin.getGame().getReference().getGameState() == GameState.GAMING || plugin.getGame().getReference().getGameState() == GameState.STARTING) {
                    stopGame();
                }
                if (args.length < 4) {
                    sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
                    return true;
                }
                process(sender, args);
            }
            return true;
        }
        if(args.length < 4) {
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
            return true;
        }
        process(sender, args);
        return true;
    }

    private void stopGame() {
        if(plugin.getGame().getReference().getGameState() == GameState.STARTING) {
            plugin.getGame().getPreTimer().cancel();
        } else if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            plugin.getGame().getTimer().cancel();
        }
        plugin.getGame().stop(GameTeams.NONE);
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_STOPPED_GAME);
    }

    private void process(CommandSender sender, String[] args) {
        Location loc;
        try {
            loc = new Location(Bukkit.getServer().getWorld(args[0]), Integer.parseInt(args[1]) + 0.5, Integer.parseInt(args[2]), Integer.parseInt(args[3]) + 0.5, 0, 0);
        } catch(NumberFormatException ex) {
            ex.printStackTrace();
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_NUM_ONLY);
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
            return;
        } catch(Exception ex) {
            ex.printStackTrace();
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_COMMAND);
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
            return;
        }
        boolean success = plugin.getGame().preStart(loc);
        if(success == false) {
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PLAYERS_NEEDED);
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_NEEDED_PLAYERS.replaceAll("%COUNT_A%", String.valueOf(plugin.getGame().getNeededPlayers())).replaceAll("%COUNT_B%", String.valueOf(plugin.getGame().getNeededPlayers() - Bukkit.getOnlinePlayers().size())));
        }
    }

}
