package me.clockclap.tct.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ProtectWorld implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(!p.isOp()) {
            e.setCancelled(true);
        }
    }

    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(!p.isOp()) {
            e.setCancelled(true);
        }
    }

}
