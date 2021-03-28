package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.CustomProjectileData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import me.clockclap.tct.item.CustomSpecialItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
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
import org.bukkit.projectiles.ProjectileSource;

import java.util.Arrays;
import java.util.List;

public class ItemEvent implements Listener {

    private NanamiTct plugin;

    public ItemEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerAttack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if(plugin.getGame().getReference().PLAYERDATA.get(p.getName()).isSpectator()) {
                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getEntity();
            Player q = (Player) e.getDamager();
            if(q.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack i = q.getInventory().getItemInMainHand();
                if(i.hasItemMeta()) {
                    for (CustomSpecialItem item : CustomItems.specialItems) {
                        if (i.getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemStack().getItemMeta().getDisplayName())) {
                            if (item.isAttackable() == false) {
                                e.setCancelled(true);
                            } else {
                                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(q.getName());
                                if(data.getRole() == GameRoles.FOX) {
                                    if(data.getWatcher() != null) {
                                        if(data.getWatcher().getCountFox() <= plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60)) {
                                            if(data.getWatcher().getCountFox() > 0) {
                                                data.getWatcher().setCountFox(plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60));
                                            }
                                        }
                                    }
                                }
                            }
                            item.onAttackPlayer(q, p);
                            if(!item.isQuickChatItem()) {
                                return;
                            }
                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(q.getName());
                            if (data.getQuickChatCooldown() <= 0) {
                                item.onAttackPlayerWithCooldown(q, p);
                                data.startQCCCountdown();
                            } else {
                                q.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_QUICK_CHAT_CURRENTLY_COOLDOWN);
                            }
                        }
                    }
                }
            }
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(q.getName());
            if(data.getRole() == GameRoles.FOX) {
                if(data.getWatcher() != null) {
                    if(data.getWatcher().getCountFox() <= plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60)) {
                        if(data.getWatcher().getCountFox() > 0) {
                            data.getWatcher().setCountFox(plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60));
                        }
                    }
                }
            }
        }
    }

    private boolean clickable = true;

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        if(clickable) {
            clickable = false;
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                clickable_ = true;
            }, 2);
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
                }
            }
        } else {
            clickable = true;
        }
    }

    private boolean clickable_ = true;

    @EventHandler
    public void playerInteractAtPlayer(PlayerInteractAtEntityEvent e) {
        if(clickable_) {
            clickable_ = false;
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                clickable_ = true;
            }, 2);
            if (e.getRightClicked() == null) {
                return;
            }
            if (e.getRightClicked() instanceof Player) {
                Player p = e.getPlayer();
                Player q = (Player) e.getRightClicked();
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(q.getName());
                if (!data.isSpectator()) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR_I);
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_NAME + ": " + q.getName());
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_HP + ": " + ChatColor.RED + q.getHealth());
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_TOGETHER + ": " + ChatColor.GREEN + data.getTogether()
                            + ChatColor.AQUA + ", " + Reference.TCT_VILLAGER + ": " + ChatColor.GREEN + data.getVillager()
                            + ChatColor.AQUA + ", " + Reference.TCT_SUS + ": " + ChatColor.GREEN + data.getSuspicious()
                            + ChatColor.AQUA + ", " + Reference.TCT_WOLF + ": " + ChatColor.GREEN + data.getWolf());
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SEPARATOR_I);
                }
            }
        } else {

        }
    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        ProjectileSource source = projectile.getShooter();
        GameRole role = GameRoles.NONE;
        if(source instanceof Player) {
            Player p = (Player) source;
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getName());
            if(data.isSpectator()) {
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
                loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 5.4F, false, false);
                plugin.getGame().getReference().PROJECTILEDATA.get(projectile).cancelTimer();
                plugin.getGame().getReference().PROJECTILEDATA.remove(projectile);
            }
        }
        if(projectile instanceof Arrow) {
            if(projectile.getShooter() instanceof Player) {
                Player p = (Player) projectile.getShooter();
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getName());
                if(data.getRole() == GameRoles.FOX) {
                    if(data.getWatcher() != null) {
                        if(data.getWatcher().getCountFox() <= plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60)) {
                            if(data.getWatcher().getCountFox() > 0) {
                                data.getWatcher().setCountFox(plugin.getTctConfig().getConfig().getInt("fox-reveal-time", 60));
                            }
                        }
                    }
                }
            }
        }
    }

}
