package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.TctEntityData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.GameTeams;
import me.clockclap.tct.item.CustomItems;
import net.minecraft.server.v1_12_R1.Explosion;
import org.apache.logging.log4j.util.ReflectionUtil;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DamageEvent implements Listener {

    private NanamiTct plugin;
    private Location respawnLoc;

    public DamageEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if(data.isSpectator()) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getEntity() != null && e.getDamager() != null) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof TNTPrimed) {
                double damage = e.getDamage();
                e.setDamage(0.0D);
                Player p = (Player) e.getEntity();
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                Location loc = p.getLocation();
                p.setVelocity(new Vector());
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    p.setVelocity(new Vector());
                    if(!data.isSpectator()) {
                        p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
                    }
                }, 1L);
                if(!data.hasSponge()) {
                    e.setDamage(damage);
                    return;
                }
                if(data.hasSponge()) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANCELLED_EXPLOSION);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1.0F, 2.0F);
                    int i = 0;
                    for (ItemStack item : p.getInventory().getContents()) {
                        if (item == null) {
                            continue;
                        }
                        if (item.hasItemMeta()) {
                            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.SPONGE.getItemStack().getItemMeta().getDisplayName())) {
                                int amt = item.getAmount() - 1;
                                if(amt <= 0) {
                                    data.setSponge(false);
                                }
                                item.setAmount(amt);
                                p.getInventory().setItem(i, amt > 0 ? item : null);
                                p.updateInventory();
                                break;
                            }
                        }
                        i++;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ItemStack[] contents = p.getInventory().getContents();
            e.getDrops().clear();
            Location loc = p.getLocation();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if (plugin.getGame().getReference().getGameState() == GameState.GAMING) {
                respawnLoc = plugin.getGame().getLocation();
                if (!data.isSpectator()) {
                    plugin.getGame().removeRemainingPlayers(data, true);
                    p.getInventory().clear();
                    List<PlayerData> villagers = new ArrayList<>();
                    List<PlayerData> wolves = new ArrayList<>();
                    List<PlayerData> foxes = new ArrayList<>();
                    for (PlayerData d : plugin.getGame().getRemainingPlayers(true)) {
                        if (d.getRole().getTeam() == GameTeams.VILLAGERS) {
                            villagers.add(d);
                            continue;
                        }
                        if (d.getRole().getTeam() == GameTeams.WOLVES) {
                            wolves.add(d);
                            continue;
                        }
                        if (d.getRole() == GameRoles.FOX) {
                            foxes.add(d);
                        }
                    }
                    if (wolves.size() > 0 && villagers.size() <= 0) {
                        plugin.getGame().getTimer().cancel();
                        if (foxes.size() > 0) {
                            plugin.getGame().stop(GameTeams.FOXES);
                            return;
                        }
                        plugin.getGame().stop(GameTeams.WOLVES);
                        return;
                    }
                    if (villagers.size() > 0 && wolves.size() <= 0) {
                        plugin.getGame().getTimer().cancel();
                        if (foxes.size() > 0) {
                            plugin.getGame().stop(GameTeams.FOXES);
                            return;
                        }
                        plugin.getGame().stop(GameTeams.VILLAGERS);
                        return;
                    }
                    if (villagers.size() <= 0 && wolves.size() <= 0) {
                        p.spigot().respawn();
                        plugin.getGame().getTimer().cancel();
                        if (foxes.size() > 0) {
                            plugin.getGame().stop(GameTeams.FOXES);
                            return;
                        }
                        plugin.getGame().stop(GameTeams.VILLAGERS);
                        return;
                    }
                    /**/
                    EntityDamageEvent event = p.getLastDamageCause();
                    EntityDamageEvent.DamageCause damageCause = event.getCause();
                    TctDeathCause cause = TctDeathCause.AIR;
                    if(data.getWatcher() != null && data.getRole() == GameRoles.FOX) {
                        data.getWatcher().cancelCountFox();
                    }
                    if(damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || damageCause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
                        cause = TctDeathCause.KILL;
                        if(p.getKiller() != null) {
                            PlayerData killer = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getKiller().getName()));
                            killer.addKilledPlayer(NanamiTct.utilities.resetColor(p.getName()));
                            data.setKilledBy(new Killer(p.getKiller(), plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getKiller().getName())).getRole()));
                            for(ItemStack item : contents) {
                                if(item != null) {
                                    if(item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.EMPTY_BOTTLE.getItemStack().getItemMeta().getDisplayName())) {
                                        int tick = 200;
                                        int level;
                                        FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();
                                        try {
                                            if (config.getString("potion-effect.slowness.duration").endsWith("t")) {
                                                String str = config.getString("potion-effect.slowness.duration");
                                                str = str.substring(0, str.length() - 1);
                                                try {
                                                    tick = Integer.parseInt(str);
                                                } catch (NumberFormatException ex) {
                                                    tick = 200;
                                                }
                                            } else if (config.getString("potion-effect.slowness.duration").endsWith("s")) {
                                                String str = config.getString("potion-effect.slowness.duration");
                                                str = str.substring(0, str.length() - 1);
                                                try {
                                                    tick = Integer.parseInt(str) * 20;
                                                } catch (NumberFormatException ex) {
                                                    tick = 200;
                                                }
                                            }
                                        } catch(NullPointerException ex) {
                                            tick = 200;
                                        }
                                        try {
                                            level = config.getInt("potion-effect.slowness.level");
                                        } catch(Exception ex) {
                                            level = 2;
                                        }
                                        p.getKiller().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, tick, level));
                                        break;
                                    }
                                }
                            }
                        } else {
                            data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                        }
                    } else if(damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || damageCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        cause = TctDeathCause.TNT;
                        data.setKilledBy(new Killer("TNT", GameRoles.WOLF, Killer.KillerCategory.TNT));
                    } else if(damageCause == EntityDamageEvent.DamageCause.FALL) {
                        cause = TctDeathCause.FALL;
                        data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                    }
                    data.kill(cause);
                }
            } else {
                respawnLoc = e.getEntity().getLocation();
            }
            p.spigot().respawn();
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(respawnLoc);
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
        if(data.isSpectator() && data.getRole() != GameRoles.SPEC && plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if(data.getKilledBy().getCategory() != Killer.KillerCategory.AIR) {
                e.getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_YOU_ARE_KILLED_BY.replaceAll("%PLAYER%", data.getKilledBy().getName()));
            }
            e.getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_YOU_ARE_SPECTATOR_MODE);
        }
    }

}
