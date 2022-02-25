package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandAboutTCT implements CommandExecutor {

    private final NanamiTct plugin;

    public CommandAboutTCT(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player && !sender.isOp()) {
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0.replaceAll("%VERSION%", plugin.getDescription().getVersion()));
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GRAY + "7mi TCT 作者: " + ChatColor.YELLOW + "ClockClap");
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GRAY + " " + " " + " 元となったプラグインの作者: " + ChatColor.YELLOW + "jack_basukeraihu & ShoboSuke");
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + "" + ChatColor.ITALIC + "元となったTrouble in Crafter Townのデコンパイル、ソースコードの使用は一切していません。");
        return true;
    }

}
