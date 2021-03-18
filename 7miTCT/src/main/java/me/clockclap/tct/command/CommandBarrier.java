package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandBarrier implements CommandExecutor {

    private NanamiTct plugin;

    public CommandBarrier(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.isOp()) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
                return true;
            }
            ItemStack item = new ItemStack(Material.BARRIER);
            p.getInventory().addItem(item);
            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_GAVE_BARRIER.replaceAll("%PLAYER%", Reference.TCT_CHAT_SYSTEM_YOU));
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLAYER_ONLY);
        return true;
    }

}
