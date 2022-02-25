package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.event.ShopOpenEvent;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandShop implements CommandExecutor {

    private final NanamiTct plugin;

    public CommandShop(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
            if(!data.isSpectator()) {
                plugin.getCustomInventory().initialize();
                if(data.getRole() == GameRoles.VILLAGER) {
                    p.openInventory(plugin.getCustomInventory().getGeneralShop());
                } else if(data.getRole() == GameRoles.HEALER) {
                    p.openInventory(plugin.getCustomInventory().getHealerShop());
                } else if(data.getRole() == GameRoles.DETECTIVE) {
                    p.openInventory(plugin.getCustomInventory().getDetectiveShop());
                } else if(data.getRole() == GameRoles.WOLF) {
                    p.openInventory(plugin.getCustomInventory().getWolfShop());
                } else if(data.getRole() == GameRoles.FANATIC) {
                    p.openInventory(plugin.getCustomInventory().getFanaticShop());
                } else if(data.getRole() == GameRoles.FOX) {
                    p.openInventory(plugin.getCustomInventory().getFoxShop());
                } else if(data.getRole() == GameRoles.IMMORAL) {
                    p.openInventory(plugin.getCustomInventory().getImmoralShop());
                } else {
                    ShopOpenEvent shopOpenEvent = new ShopOpenEvent(plugin.getGame(), p);
                    Bukkit.getPluginManager().callEvent(shopOpenEvent);
                }
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_YOUR_COIN.replaceAll("%COUNT%", String.valueOf(data.getCoin())));
                return true;
            }
            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_USE);
            return true;
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLAYER_ONLY);
        return true;
    }
}
