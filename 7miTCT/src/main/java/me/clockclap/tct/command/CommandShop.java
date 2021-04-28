package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShop implements CommandExecutor {

    private NanamiTct plugin;

    public CommandShop(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if(!data.isSpectator()) {
                plugin.getCustomInventory().initialize();
                if(data.getRole() == GameRoles.WOLF) {
                    p.openInventory(plugin.getCustomInventory().getWolfShop());
                } else if(data.getRole() == GameRoles.DETECTIVE) {
                    p.openInventory(plugin.getCustomInventory().getDetectiveShop());
                } else {
                    p.openInventory(plugin.getCustomInventory().getGeneralShop());
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
