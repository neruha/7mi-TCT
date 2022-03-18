package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.VersionUtils;
import me.clockclap.tct.api.CooldownTypes;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.data.TctCustomBlockData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

import java.util.Arrays;
import java.util.Optional;

public class BlockListener implements Listener {

    private final NanamiTct plugin;

    public BlockListener(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).isSpectator()) {
            if (CustomItems.generalBlocks.size() != 0) {
                ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                ItemStack offItem = e.getPlayer().getInventory().getItemInOffHand();
                if (item.hasItemMeta()) {
                    for (CustomBlock block : CustomItems.generalBlocks) {
                        if ((item.getItemMeta().getDisplayName().equalsIgnoreCase(block.getItemStack().getItemMeta().getDisplayName())) ||
                                (offItem != null && offItem.hasItemMeta() &&
                                        offItem.getItemMeta().getDisplayName().equalsIgnoreCase(block.getItemStack().getItemMeta().getDisplayName()))) {
                            if (block.isPlaceable()) {
                                TctCustomBlockData data = new TctCustomBlockData(plugin.getGame(), block, e.getBlockPlaced());
                                CustomBlockInfo.blockDataList.add(data);
                                if (block == CustomItems.HEAL_STATION) {
                                    if (NanamiTct.playerStats != null) {
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
                        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(item_.getItemStack().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
            if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
        } else {
            if (!e.getPlayer().isOp()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (!plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).isSpectator()) {
            if (CustomItems.generalBlocks.size() != 0) {
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
            if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
        } else {
            if (!e.getPlayer().isOp()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        PlayerData d = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
        if (d != null) {
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
                                            final int time = NanamiTct.plugin.getTctConfig().getConfig().getInt("heal-station-respawn-time", 5);

                                            if (VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2)) {
                                                block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation(), 100, block.getState().getBlockData());

                                                for (Entity entity :block.getWorld().getNearbyEntities(block.getLocation(), 6,6,6)) {
                                                    if (entity instanceof Player) {
                                                        ((Player) entity).playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 50, 1);
                                                    }
                                                }
                                            } else {

                                                Location clone = block.getLocation().clone().add(0.5, 0.5, 0.5);

                                                block.getWorld().spawnParticle(Particle.BLOCK_CRACK, clone.getX(), clone.getY(), clone.getZ(), 100, CustomItems.HEAL_STATION.getMaterial().getData());
                                            }
                                            block.breakNaturally(new ItemStack(Material.AIR));

                                            final Location location = block.getLocation();
                                            Bukkit.getScheduler().runTaskLater(NanamiTct.plugin, () -> {
                                                if (NanamiTctApi.game.getReference().getGameState() == GameState.GAMING)
                                                    location.getBlock().setType(CustomItems.HEAL_STATION.getMaterial());
                                            }, time * 20L);
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
                                                if (NanamiTct.playerStats != null) {
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
        for (DeadBody deadBody : plugin.getGame().getReference().DEADBODIES) {

            if (block.getLocation().equals(deadBody.getLocation())) {
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId());

                if (data.isSpectator()) {
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_NAME + ": " + deadBody.getName());
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_ROLE + ": " + deadBody.getRole().getDisplayName());
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_TIME_AFTER_DEATH + ": " + deadBody.getTimeAfterDeath());
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_CAUSE_OF_DEATH + ": " + deadBody.getCause().getName());
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
                } else {
                    if (deadBody.isDamaged()) {
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_DAMAGED);

                        if (!deadBody.isFound()) {
                            handleBook(deadBody, Reference.TCT_CHAT_DEADBODY_DAMAGED, player);
                        }

                    } else {
                        ItemStack itemStack = player.getInventory().getItemInMainHand();

                        final ItemStack hasTorch = itemStack.hasItemMeta()
                                && itemStack.getItemMeta().hasDisplayName()
                                && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.TORCH.getItemStack().getItemMeta().getDisplayName())
                                ? itemStack : null;

                        if (hasTorch != null && data.getRole() == GameRoles.WOLF) {

                            deadBody.setDamaged(true);

                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_DAMAGED_SUCCESS);
                            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1f, 1f);

                            if (NanamiTctApi.isPlayerStatsNotNull()) {
                                NanamiTctApi.playerStats.getStat(data.getUniqueId()).increaseItemUsed();
                            }

                        } else {

                            //棒をクリックする前に通知を出したいので先に発見表示を出す

                            if (!deadBody.isFound() && !deadBody.isFake()) {
                                handleBook(deadBody, deadBody.getPlayer().getName(), player);
                            }

                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_NAME + ": " + deadBody.getName());
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_ROLE + ": " + deadBody.getRole().getDisplayName());
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_TIME_AFTER_DEATH + ": " + deadBody.getTimeAfterDeath());
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_CAUSE_OF_DEATH + ": " + deadBody.getCause().getName());
                            player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR);

                            final ItemStack hasStick = itemStack.hasItemMeta()
                                    && itemStack.getItemMeta().hasDisplayName()
                                    && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.STICK.getItemStack().getItemMeta().getDisplayName()) ? itemStack : null;

                            if (hasStick != null) {
                                if (deadBody.getRole() == GameRoles.WOLF) {
                                    if (deadBody.getKilledPlayers().size() > 0) {
                                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_KILLED_PLAYERS + ": [" + String.join(", ", deadBody.getKilledPlayers()) + "]");
                                    } else {
                                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_HAS_NOT_KILLED);
                                    }

                                    //人狼だった場合のみ減らす
                                    hasStick.setAmount(hasStick.getAmount() - 1);
                                } else {
                                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_IS_NOT_WOLF);
                                }

                                if (NanamiTctApi.isPlayerStatsNotNull()) {
                                    NanamiTctApi.playerStats.getStat(data.getUniqueId()).increaseItemUsed();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void handleBook(DeadBody deadBody, String displayName, Player player) {
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DEADBODY_FOUND.replaceAll("%PLAYER%", displayName));
        deadBody.setFound(true);
        TctLog log = plugin.getGame().getLog();
        log.addLine(Reference.TCT_LOGBOOK_FOUND_DEADBODY);
        log.addLine(deadBody.getName());
        log.update();

        if (NanamiTctApi.isPlayerStatsNotNull()) {
            NanamiTct.playerStats.getStat(player.getUniqueId()).increaseTotalFoundDeadBodies();
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