package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.CooldownTypes;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
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
        if(!plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName())).isSpectator()) {
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
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
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
        if(!plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName())).isSpectator()) {
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
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
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
            clickable = false;
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                clickable = true;
            }, 2);
            if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                if(e.getAction() == Action.RIGHT_CLICK_AIR) {
                    block = data.getTargetBlock(5);
                    processDeadBody(e.getPlayer(), block);
                }
            }
            if(!plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName())).isSpectator()) {
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
                boolean visible = true;
                boolean can = true;
                if(deadBody.isDamaged()) {
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_DAMAGED);
                    if(!plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(player.getName())).isSpectator()) {
                        can = false;
                    }
                }
                if(can) {
                    if (deadBody.isFound()) {
                        if (player.getInventory().getItemInMainHand() != null &&
                                player.getInventory().getItemInMainHand().hasItemMeta() &&
                                player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.TORCH.getItemStack().getItemMeta().getDisplayName())) {
                            deadBody.setDamaged(true);
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_DAMAGED_SUCCESS);
                            for (int i = 0; i < player.getInventory().getSize(); i++) {
                                ItemStack item = player.getInventory().getItem(i);
                                if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                                    int amt = item.getAmount() - 1;
                                    item.setAmount(amt);
                                    player.getInventory().setItem(i, amt > 0 ? item : null);
                                    player.updateInventory();
                                    break;
                                }
                            }
                        } else if (player.getInventory().getItemInMainHand() != null &&
                                player.getInventory().getItemInMainHand().hasItemMeta() &&
                                player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.STICK.getItemStack().getItemMeta().getDisplayName())) {
                            if (deadBody.getRole() == GameRoles.WOLF) {
                                if (deadBody.getKilledPlayers().size() > 0) {
                                    String str = String.join(", ", deadBody.getKilledPlayers());
                                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_KILLED_PLAYERS + ": [" + str + "]");
                                } else {
                                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_HAS_NOT_KILLED);
                                }
                                for (int i = 0; i < player.getInventory().getSize(); i++) {
                                    ItemStack item = player.getInventory().getItem(i);
                                    if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                                        int amt = item.getAmount() - 1;
                                        item.setAmount(amt);
                                        player.getInventory().setItem(i, amt > 0 ? item : null);
                                        player.updateInventory();
                                        break;
                                    }
                                }
                            } else {
                                player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_IS_NOT_WOLF);
                            }
                            visible = false;
                        } else {
                            if (visible) {
                                player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ALREADY_FOUND);
                            }
                        }
                    } else {
                        if (visible) {
                            if (!plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(player.getName())).isSpectator()) {
                                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_FOUND.replaceAll("%PLAYER%", deadBody.getPlayer().getDisplayName()));
                                deadBody.setFound(true);
                                TctLog log = plugin.getGame().getLog();
                                log.addLine(Reference.TCT_LOGBOOK_FOUND_DEADBODY);
                                log.addLine(deadBody.getName());
                                log.update();
                            }
                        } else {
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_HAS_NOT_BEEN_FOUND);
                        }
                    }
                    if (visible) {
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_NAME + ": " + deadBody.getName());
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_ROLE + ": " + deadBody.getRole().getDisplayName());
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_TIME_AFTER_DEATH + ": " + deadBody.getTimeAfterDeath());
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_CAUSE_OF_DEATH + ": " + deadBody.getCause().getName());
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onExplodeBlock(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

}
