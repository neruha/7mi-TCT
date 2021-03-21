package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.item.CustomItems;
import me.clockclap.tct.item.CustomSpecialItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemEvent implements Listener {

    private NanamiTct plugin;

    public ItemEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerAttack(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getEntity();
            Player q = (Player) e.getDamager();
            for(CustomSpecialItem item : CustomItems.specialItems) {
                if(q.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemStack().getItemMeta().getDisplayName())) {
                    if(item.isAttackable() == false) {
                        e.setCancelled(true);
                    }
                    item.onAttackPlayer(q, p);
                    PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(q.getName());
                    if(data.getQuickChatCooldown() <= 0) {
                        item.onAttackPlayerWithCooldown(q, p);
                        data.startQCCCountdown();
                    } else {
                        q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_QUICK_CHAT_CURRENTLY_COOLDOWN);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        for(CustomSpecialItem item : CustomItems.specialItems) {
            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemStack().getItemMeta().getDisplayName())) {
                if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    item.onRightClick(e.getPlayer());
                }
                if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    item.onLeftClick(e.getPlayer());
                }
            }
        }
    }

}
