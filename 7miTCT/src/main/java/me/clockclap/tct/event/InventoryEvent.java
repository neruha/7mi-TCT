package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryEvent implements Listener {

    private NanamiTct plugin;

    public InventoryEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getGeneralShop().getTitle()) ||
                e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getDetectiveShop().getTitle()) ||
                e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getWolfShop().getTitle())) {
            if(e.getCurrentItem().getType() != Material.AIR) {
                e.setCancelled(true);
                if(e.getWhoClicked() instanceof Player) {
                    Player p = (Player) e.getWhoClicked();
                    if(e.getCurrentItem().hasItemMeta()) {
                        ItemMeta meta = e.getCurrentItem().getItemMeta();
                        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getName());
                        if(data.getCoin() <= 0) {
                            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_NOT_ENOUGH_COINS);
                            return;
                        }
                        for (CustomItem i : CustomItems.allItems) {
                            if(meta.getDisplayName().equalsIgnoreCase(i.getItemStack().getItemMeta().getDisplayName())) {
                                if(data.getBoughtItem().contains(i.getName())) {
                                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ALREADY_BOUGHT);
                                    return;
                                }
                                data.setCoin(data.getCoin() - 1);
                                data.addBoughtItem(i.getName());
                                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_BOUGHT_ITEM.replaceAll("%ITEM%", i.getName()));
                                p.getInventory().addItem(e.getCurrentItem());
                                return;
                            }
                        }
                    }
                    p.closeInventory();
                }
            }
        }
    }

}
