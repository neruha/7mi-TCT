package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandItem implements TabExecutor {

    private NanamiTct plugin;

    public CommandItem(NanamiTct plugin) {
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
                    String name = NanamiTct.utilities.resetColor(p.getName());
                    if (name.equalsIgnoreCase(str)) {
                        isAdmin = true;
                        break;
                    }
                }
            }
            if(isAdmin == false) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
                return true;
            }
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("LOG_BOOK")) {
                    p.getInventory().addItem(plugin.getGame().getLog().getItem());
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_GAVE_ITEM.replaceAll("%ITEM%", "LOG_BOOK"));
                    return true;
                }
                for(CustomItem i : CustomItems.allItems) {
                    if(i.getName().equalsIgnoreCase(args[0])) {
                        process(p, i.getItemStack());
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_GAVE_ITEM.replaceAll("%ITEM%", i.getName()));
                        return true;
                    }
                }
                p.sendMessage(Reference.TCT_CHATPREFIX + ChatColor.RED + " Usage: /i <ITEM NAME>");
                return true;
            }
            p.sendMessage(Reference.TCT_CHATPREFIX + ChatColor.RED + " Usage: /i <ITEM NAME>");
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLAYER_ONLY);
        return true;
    }

    private void process(Player player, ItemStack item) {
        player.getInventory().addItem(item);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
