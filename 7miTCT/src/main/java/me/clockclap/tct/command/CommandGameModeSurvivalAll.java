package me.clockclap.tct.command;

import me.clockclap.tct.api.Reference;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGameModeSurvivalAll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.isOp()) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
                return true;
            }
            process(sender);
            return true;
        }
        process(sender);
        return true;
    }

    private void process(CommandSender sender) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PROCESS_CHANGE_GAMEMODE.replaceAll("%PLAYER%", Reference.TCT_CHAT_SYSTEM_YOU).replaceAll("%GAMEMODE%", Reference.TCT_GAMEMODE_SURVIVAL));
            p.setGameMode(GameMode.SURVIVAL);
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PROCESS_CHANGE_GAMEMODE.replaceAll("%PLAYER%", Reference.TCT_CHAT_SYSTEM_EVERYONE).replaceAll("%GAMEMODE%", Reference.TCT_GAMEMODE_SURVIVAL));
    }

}
