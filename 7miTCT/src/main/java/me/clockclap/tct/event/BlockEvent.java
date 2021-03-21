package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.item.CustomBlock;
import me.clockclap.tct.item.CustomBlockInfo;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class BlockEvent implements Listener {

    private NanamiTct plugin;

    public BlockEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if(plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName()).isSpectator() == false) {
            if(CustomItems.generalBlocks.size() != 0) {
                for (CustomBlock block : CustomItems.generalBlocks) {
                    if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(block.getItemStack().getItemMeta().getDisplayName())) {
                        if (block.isPlaceable()) {
                            CustomBlockInfo.blockLocationMap.get(block).add(e.getBlock().getLocation());
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        } else {
            if(!e.getPlayer().isOp()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if(plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName()).isSpectator() == false) {
            if(CustomItems.generalBlocks.size() != 0) {
                for (CustomBlock block : CustomItems.generalBlocks) {
                    for (Location loc : CustomBlockInfo.blockLocationMap.get(block)) {
                        if (loc == e.getBlock().getLocation()) {
                            if (block.isBreakable()) {
                                CustomBlockInfo.blockLocationMap.get(block).remove(loc);
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        } else {
            if(!e.getPlayer().isOp()) {
                e.setCancelled(true);
            }
        }
    }

}
