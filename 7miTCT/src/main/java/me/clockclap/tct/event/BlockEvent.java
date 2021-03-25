package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.CooldownTypes;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.item.CustomBlock;
import me.clockclap.tct.item.CustomBlockInfo;
import me.clockclap.tct.item.CustomItems;
import me.clockclap.tct.item.CustomSpecialItem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Ref;
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
            Block block = e.getClickedBlock();
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(!e.getPlayer().getInventory().getItemInMainHand().getType().isBlock() || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                    clickable = false;
                }
            }
            if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                clickable = true;
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName());
                if(e.getAction() == Action.RIGHT_CLICK_AIR) {
                    block = data.getTargetBlock(5);
                    processDeadBody(e.getPlayer(), block);
                }
            }
            if(!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName()).isSpectator()) {
                if (CustomItems.generalBlocks.size() != 0) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        for (CustomBlockData data : CustomBlockInfo.blockDataList) {
                            if (data.getLocation().getBlockX() == block.getLocation().getBlockX() && data.getLocation().getBlockY() == block.getLocation().getBlockY() && data.getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
                                String cooldownMsg = Reference.TCT_CHATPREFIX + " " + Reference.TCT_QUICK_CHAT_CURRENTLY_COOLDOWN;
                                if (data.getCooldown() > 0) {
                                    e.getPlayer().sendMessage(cooldownMsg);
                                    break;
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
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                processDeadBody(e.getPlayer(), block);
            }
        } else {
            clickable = true;
        }
    }

    public void processDeadBody(Player player, Block block) {
        for(DeadBody deadBody : plugin.getGame().getReference().DEADBODIES) {
            Location loc = deadBody.getLocation();
            if(block.getLocation().getBlockX() == loc.getBlockX() &&
                    block.getLocation().getBlockY() == loc.getBlockY() &&
                    block.getLocation().getBlockZ() == loc.getBlockZ()) {
                clickable = true;
                if(deadBody.isFound()) {
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ALREADY_FOUND);
                } else {
                    if(!plugin.getGame().getReference().PLAYERDATA.get(player.getName()).isSpectator()) {
                        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_FOUND.replaceAll("%PLAYER%", deadBody.getPlayer().getDisplayName()));
                        deadBody.setFound(true);
                    }
                }
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_NAME + ": " + deadBody.getName());
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_ROLE + ": " + deadBody.getRole().getDisplayName());
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_TIME_AFTER_DEATH + ": " + deadBody.getTimeAfterDeath());
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_CAUSE_OF_DEATH + ": " + deadBody.getCause().getName());
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

}
