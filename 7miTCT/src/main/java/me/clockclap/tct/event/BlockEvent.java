package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.CooldownTypes;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.game.data.TctCustomBlockData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.*;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
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

import java.util.List;

public class BlockEvent implements Listener {

    private NanamiTct plugin;

    public BlockEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if(!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).isSpectator()) {
            if(CustomItems.generalBlocks.size() != 0) {
                ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                if(item.hasItemMeta()) {
                    for (CustomBlock block : CustomItems.generalBlocks) {
                        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(block.getItemStack().getItemMeta().getDisplayName())) {
                            if (block.isPlaceable()) {
                                TctCustomBlockData data = new TctCustomBlockData(plugin.getGame(), block, e.getBlockPlaced());
                                CustomBlockInfo.blockDataList.add(data);
                                if(block == CustomItems.HEAL_STATION) {
                                    if(NanamiTct.playerStats != null) {
                                        PlayerStat stat = NanamiTct.playerStats.getStat(e.getPlayer().getUniqueId());
                                        stat.setTotalPlaceHealStation(stat.getTotalPlaceHealStation() + 1);
                                    }
                                }

                                if (block.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.LANDMINE.getItemStack().getItemMeta().getDisplayName())) {
                                    data.runTimer(CooldownTypes.LANDMINE);
                                    e.getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_LANDMINE_PLACED.replaceAll("%SECOND%", String.valueOf(NanamiTct.plugin.getTctConfig().getConfig().getInt("landmine-cooldown", 5))));
                                }
                            } else {
                                e.setCancelled(true);
                            }
                            return;
                        }
                    }
                    for (CustomSpecialItem item_ : CustomItems.specialItems) {
                        if(item.getItemMeta().getDisplayName().equalsIgnoreCase(item_.getItemStack().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
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
        if(!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).isSpectator()) {
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
        PlayerData d = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
        if(d != null) {
            if (d.isClickableBlock()) {
                Block block = e.getClickedBlock();
                d.setClickableBlock(false);
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    d.setClickableBlock(true);
                }, 2);
                if (e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                    PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
                    if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                        block = data.getTargetBlock(5);
                        processDeadBody(e.getPlayer(), block);
                    }
                }
                if (!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).isSpectator()) {
                    if (CustomItems.generalBlocks.size() != 0) {
                        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            for (CustomBlockData data : CustomBlockInfo.blockDataList) {
                                if (data.getLocation().getBlockX() == block.getLocation().getBlockX() && data.getLocation().getBlockY() == block.getLocation().getBlockY() && data.getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
                                    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                                        data.getCustomBlock().onLeftClick(e.getPlayer());
                                        if (data.getCustomBlock().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.HEAL_STATION.getItemStack().getItemMeta().getDisplayName())) {
                                            int t = NanamiTct.plugin.getTctConfig().getConfig().getInt("heal-station-respawn-time", 5);
//                                        block.setType(Material.AIR);
//                                        block.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5,0.5,0.5), 100, new MaterialData(CustomItems.HEAL_STATION.getMaterial()));
                                            block.breakNaturally(new ItemStack(Material.AIR));
                                            @SuppressWarnings("deprecation")
                                            PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001, new BlockPosition(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()), Material.REDSTONE_ORE.getId(), false);
                                            List<Player> players = e.getPlayer().getWorld().getPlayers();
                                            for(final Player p : players) {
                                                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                                            }
                                            final Location[] l = {block.getLocation()};
                                            Bukkit.getScheduler().runTaskLater(NanamiTct.plugin, () -> {
                                                if(NanamiTctApi.game.getReference().getGameState() == GameState.GAMING)
                                                    l[0].getBlock().setType(CustomItems.HEAL_STATION.getMaterial());
                                            }, t * 20L);
                                        }
                                    }
                                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                        String cooldownMsg = Reference.TCT_CHATPREFIX + " " + Reference.TCT_QUICK_CHAT_CURRENTLY_COOLDOWN;
                                        if (data.getCooldown() > 0) {
                                            e.getPlayer().sendMessage(cooldownMsg);
                                            break;
                                        } else {
                                            data.getCustomBlock().onRightClick(e.getPlayer());
                                            if (data.getCustomBlock().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.HEAL_STATION.getItemStack().getItemMeta().getDisplayName())) {
                                                data.runTimer(CooldownTypes.HEAL_STATION);
                                                if(NanamiTct.playerStats != null) {
                                                    PlayerStat stat = NanamiTct.playerStats.getStat(e.getPlayer().getUniqueId());
                                                    stat.setTotalUseHealStation(stat.getTotalUseHealStation() + 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    processDeadBody(e.getPlayer(), block);
                }
            } else {
                d.setClickableBlock(true);
            }
        }
    }

    public void processDeadBody(Player player, Block block) {
        for(DeadBody deadBody : plugin.getGame().getReference().DEADBODIES) {
            Location loc = deadBody.getLocation();
            if(block.getLocation().getBlockX() == loc.getBlockX() &&
                    block.getLocation().getBlockY() == loc.getBlockY() &&
                    block.getLocation().getBlockZ() == loc.getBlockZ()) {
                boolean visible = true;
                boolean can = true;
                if(deadBody.isDamaged()) {
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_DAMAGED);
                    if(!plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId()).isSpectator()) {
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
                            if(MySQLStatus.isSqlEnabled() && NanamiTct.playerStats != null) {
                                PlayerStat stat = NanamiTct.playerStats.getStat(player.getUniqueId());
                                if(stat != null) stat.setCountUsedItem(stat.getCountUsedItem() + 1);
                            }
                            return;
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
                                if(MySQLStatus.isSqlEnabled() && NanamiTct.playerStats != null) {
                                    PlayerStat stat = NanamiTct.playerStats.getStat(player.getUniqueId());
                                    if(stat != null) stat.setCountUsedItem(stat.getCountUsedItem() + 1);
                                }
                                return;
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
                            if (!plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId()).isSpectator() && !deadBody.isFake()) {
                                if(NanamiTct.playerStats != null) {
                                    PlayerStat stat = NanamiTct.playerStats.getStat(player.getUniqueId());
                                    stat.setTotalFoundDeadBodies(stat.getTotalFoundDeadBodies() + 1);
                                }
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
