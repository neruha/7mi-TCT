package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class CancelHunger implements Listener {

    private NanamiTct plugin;

    public CancelHunger(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
