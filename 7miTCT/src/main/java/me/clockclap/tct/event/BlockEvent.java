package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.CooldownTypes;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.item.CustomBlock;
import me.clockclap.tct.item.CustomBlockInfo;
import me.clockclap.tct.item.CustomItems;
import me.clockclap.tct.item.CustomSpecialItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BlockEvent implements Listener {

    private NanamiTct plugin;

    public BlockEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if(!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName()).isSpectator()) {
            if(CustomItems.generalBlocks.size() != 0) {
                ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                if(item.hasItemMeta()) {
                    for (CustomBlock block : CustomItems.generalBlocks) {
                        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(block.getItemStack().getItemMeta().getDisplayName())) {
                            if (block.isPlaceable()) {
                                CustomBlockInfo.blockDataList.add(new CustomBlockData(plugin.getGame(), block, e.getBlockPlaced()));
                            } else {
                                e.setCancelled(true);
                            }
                            return;
                        }
                    }
                }
            }
            e.setCancelled(true);
            return;
        } else {
            if(!e.getPlayer().isOp()) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if(!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName()).isSpectator()) {
            if(CustomItems.generalBlocks.size() != 0) {
                for (CustomBlockData data : CustomBlockInfo.blockDataList) {
                    if (data.getLocation().getBlockX() == e.getBlock().getLocation().getBlockX() && data.getLocation().getBlockY() == e.getBlock().getLocation().getBlockY() && data.getLocation().getBlockZ() == e.getBlock().getLocation().getBlockZ()) {
                        if (data.getCustomBlock().isBreakable()) {
                            CustomBlockInfo.blockDataList.remove(data);
                        } else {
                            e.setCancelled(true);
                        }
                        return;
                    }
                }
            }
            e.setCancelled(true);
            return;
        } else {
            if(!e.getPlayer().isOp()) {
                e.setCancelled(true);
                return;
            }
        }
    }

    private boolean clickable = true;

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        if(clickable) {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(!e.getPlayer().getInventory().getItemInMainHand().getType().isBlock() || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                    clickable = false;
                }
            }
            if(!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName()).isSpectator()) {
                if (CustomItems.generalBlocks.size() != 0) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        Block block = e.getClickedBlock();
                        for (CustomBlockData data : CustomBlockInfo.blockDataList) {
                            if (data.getLocation().getBlockX() == block.getLocation().getBlockX() && data.getLocation().getBlockY() == block.getLocation().getBlockY() && data.getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
                                String cooldownMsg = Reference.TCT_CHATPREFIX + " " + Reference.TCT_QUICK_CHAT_CURRENTLY_COOLDOWN;
                                if (data.getCooldown() > 0) {
                                    e.getPlayer().sendMessage(cooldownMsg);
                                    return;
                                } else {
                                    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                                        data.getCustomBlock().onLeftClick(e.getPlayer());
                                    }
                                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                        data.getCustomBlock().onRightClick(e.getPlayer());
                                    }

                                    data.runTimer(CooldownTypes.HEAL_STATION);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            clickable = true;
        }
    }

}
