package me.clockclap.tct.command;

import me.clockclap.tct.api.Reference;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAboutTCT implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && !((Player) sender).isOp()) {
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GRAY + "7mi TCT バージョン: 1.0");
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GRAY + "7mi TCT 作者: \n"
                + ChatColor.GREEN + "By " + ChatColor.YELLOW + "ClockClap"
                + ChatColor.GREEN + "Original by " + ChatColor.YELLOW + "ShoboSuke & jack_basukeraihu\n");
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + "" + ChatColor.ITALIC + "オリジナルのTrouble in Crafter Townのデコンパイル、ソースコードの使用は\n"
                + "一切していません。");
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GRAY + "こちらのプラグインのソースコードは、希望すれば誰でも渡します。\n"
                + "ただし、許可されていないソースコードの配布はしないでください。");
        return true;
    }

}
