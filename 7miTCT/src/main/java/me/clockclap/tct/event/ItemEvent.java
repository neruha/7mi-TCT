package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.game.data.CustomProjectileData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomBlockInfo;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemEvent implements Listener {

    private NanamiTct plugin;

    public ItemEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerAttack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Firework) {
            e.setCancelled(true);
            return;
        }
        if(e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if(data.isSpectator()) {
                e.setCancelled(true);
            }
            if(data.isInvisible()) {
                data.setInvisible(false);
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
        if (e.getEntity() instanceof Player) {
            if(e.getDamager() instanceof Player || e.getDamager() instanceof Projectile) {
                Player p;
                Player q;
                if(e.getDamager() instanceof Player) {
                    p = (Player) e.getEntity();
                    q = (Player) e.getDamager();
                } else if(e.getDamager() instanceof Projectile) {
                    p = (Player) e.getEntity();
                    if(((Projectile) e.getDamager()).getShooter() instanceof Player) {
                        q = (Player) ((Projectile) e.getDamager()).getShooter();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
                if (q.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    ItemStack i = q.getInventory().getItemInMainHand();
                    if (i.hasItemMeta()) {
                        for (CustomSpecialItem item : CustomItems.specialItems) {
                            if (i.getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemStack().getItemMeta().getDisplayName())) {
                                if (item.isAttackable() == false) {
                                    e.setCancelled(true);
                                } else {
                                    PlayerData data_ = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                                    PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(q.getName()));
                                    if (data.getRole() == GameRoles.WOLF && data_.getRole() == GameRoles.WOLF) {
                                        q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_ATTACK_WOLF);
                                        e.setCancelled(true);
                                        return;
                                    }
                                    if (data.getRole() == GameRoles.FOX) {
                                        if (data_.getRole() == GameRoles.FOX) {
                                            q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_ATTACK_FOX);
                                            e.setCancelled(true);
                                            return;
                                        }
                                        if (data.getWatcher() != null) {
                                            if (data.getWatcher().getCountFox() <= plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60)) {
                                                if (data.getWatcher().getCountFox() > 0) {
                                                    data.getWatcher().setCountFox(plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60));
                                                }
                                            }
                                        }
                                    }
                                }
                                item.onAttackPlayer(q, p);
                                if (!item.isQuickChatItem()) {
                                    return;
                                }
                                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(q.getName()));
                                if (data.getQuickChatCooldown() <= 0) {
                                    item.onAttackPlayerWithCooldown(q, p);
                                    data.startQCCCountdown();
                                } else {
                                    q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_QUICK_CHAT_CURRENTLY_COOLDOWN);
                                }
                                return;
                            }
                        }
                    }
                }
                PlayerData data_ = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(q.getName()));
                if (data.getRole() == GameRoles.WOLF && data_.getRole() == GameRoles.WOLF) {
                    q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_ATTACK_WOLF);
                    e.setCancelled(true);
                    return;
                }
                if (data.getRole() == GameRoles.FOX) {
                    if (data_.getRole() == GameRoles.FOX) {
                        q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANNOT_ATTACK_FOX);
                        e.setCancelled(true);
                        return;
                    }
                    if (data.getWatcher() != null) {
                        if (data.getWatcher().getCountFox() <= plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60)) {
                            if (data.getWatcher().getCountFox() > 0) {
                                data.getWatcher().setCountFox(plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
        if(data != null) {
            if (data.isClickableItem()) {
                data.setClickableItem(false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        data.setClickableItem(true);
                    }
                }.runTaskLater(plugin, 2);
                if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
                    ItemStack i = e.getPlayer().getInventory().getItemInMainHand();
                    if (i.hasItemMeta()) {
                        for (CustomSpecialItem item : CustomItems.specialItems) {
                            if (i.getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemStack().getItemMeta().getDisplayName())) {
                                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                    item.onRightClick(e.getPlayer());
                                }
                                if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                                    item.onLeftClick(e.getPlayer());
                                }
                            }
                        }
                        if (i.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.TNT.getItemStack().getItemMeta().getDisplayName())) {
                            e.getPlayer().getInventory().setHelmet(i);
                            e.getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_EXPLODE_IN);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                    if(data != null) {
                                        data.setSponge(false);
                                    }
                                    Location loc = new Location(e.getPlayer().getLocation().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY() + 1, e.getPlayer().getLocation().getZ());
                                    TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
                                    tnt.setYield(6.0F);
                                    tnt.setFuseTicks(0);
                                }
                            }.runTaskLater(plugin, 60);
                            for (int j = 0; j < e.getPlayer().getInventory().getSize(); j++) {
                                ItemStack item = e.getPlayer().getInventory().getItem(j);
                                if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                                    int amt = item.getAmount() - 1;
                                    item.setAmount(amt);
                                    e.getPlayer().getInventory().setItem(j, amt > 0 ? item : null);
                                    e.getPlayer().updateInventory();
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                data.setClickableItem(true);
            }
        }
    }

    private boolean clickable_ = true;

    @EventHandler
    public void playerInteractAtPlayer(PlayerInteractAtEntityEvent e) {
        PlayerData d = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
        if(d != null) {
            if (d.isClickableEntity()) {
                d.setClickableEntity(false);
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    d.setClickableEntity(true);
                }, 2);
                if (e.getRightClicked() == null) {
                    return;
                }
                if (e.getRightClicked() instanceof Player) {
                    Player p = e.getPlayer();
                    Player q = (Player) e.getRightClicked();
                    PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(q.getName()));
                    if (!data.isSpectator() && !data.isInvisible()) {
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_NAME + ": " + NanamiTct.utilities.resetColor(q.getName()));
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_HP + ": " + ChatColor.RED + q.getHealth());
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_TOGETHER + ": " + ChatColor.GREEN + data.getTogether()
                                + ChatColor.AQUA + ", " + Reference.TCT_VILLAGER + ": " + ChatColor.GREEN + data.getVillager()
                                + ChatColor.AQUA + ", " + Reference.TCT_SUS + ": " + ChatColor.GREEN + data.getSuspicious()
                                + ChatColor.AQUA + ", " + Reference.TCT_WOLF + ": " + ChatColor.GREEN + data.getWolf());
                    }
                }
            } else {
                d.setClickableEntity(true);
            }
        }
    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        ProjectileSource source = projectile.getShooter();
        GameRole role = GameRoles.NONE;
        if(source instanceof Player) {
            Player p = (Player) source;
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if(data.isSpectator() || data.isInvisible()) {
                e.setCancelled(true);
                return;
            } else {
                role = data.getRole();
            }
        }
        if(projectile instanceof Snowball) {
            plugin.getGame().getReference().PROJECTILEDATA.put(projectile, new CustomProjectileData(plugin.getGame(), projectile, role));
            plugin.getGame().getReference().PROJECTILEDATA.get(projectile).startTimer();
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if(projectile instanceof Snowball) {
            Location loc = projectile.getLocation();
            if(loc != null) {
                TNTPrimed tnt = loc.getWorld().spawn(loc, TNTPrimed.class);
                tnt.setYield(6.0F);
                tnt.setFuseTicks(0);
//                loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 5.4F, false, false);
                plugin.getGame().getReference().PROJECTILEDATA.get(projectile).cancelTimer();
                plugin.getGame().getReference().PROJECTILEDATA.remove(projectile);
            }
        }
        if(projectile instanceof Arrow) {
            if(e.getHitBlock() != null) {
                Location loc = e.getHitBlock().getLocation();
                if (loc != null) {
                    List<CustomBlockData> dataList = new ArrayList<>(CustomBlockInfo.blockDataList);
                    for (CustomBlockData data : dataList) {
                        if (data != null) {
                            if (data.getLocation().getBlockX() == loc.getBlockX() &&
                                    data.getLocation().getBlockY() == loc.getBlockY() &&
                                    data.getLocation().getBlockZ() == loc.getBlockZ() &&
                                    data.getCustomBlock().getItemStack().getItemMeta().getDisplayName().
                                            equalsIgnoreCase(CustomItems.LANDMINE.getItemStack().getItemMeta().getDisplayName())) {
                                data.getBlock().setType(Material.AIR);
                                CustomBlockInfo.blockDataList.remove(dataList.indexOf(data));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

}
