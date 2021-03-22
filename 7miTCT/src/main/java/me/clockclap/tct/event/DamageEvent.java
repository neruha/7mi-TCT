package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    private NanamiTct plugin;

    public DamageEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(plugin.getGame().getReference().PLAYERDATA.get(p.getName()).isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

}
