package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DamageListener implements Listener {

    private final NanamiTct plugin;
    private Location respawnLoc;

    public DamageListener(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
            if (data.isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof TNTPrimed) {
            double damage = e.getDamage();
            e.setDamage(0.0D);
            Player p = (Player) e.getEntity();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
            Location loc = p.getLocation();
            p.setVelocity(new Vector());
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                p.setVelocity(new Vector());
                if (!data.isSpectator()) {
                    p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
                }
            }, 1L);
            if (!data.hasSponge()) {
                e.setDamage(damage);
                return;
            }
            if (data.hasSponge()) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CANCELLED_EXPLOSION);
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1.0F, 2.0F);
                int i = 0;
                for (ItemStack item : p.getInventory().getContents()) {
                    if (item == null) {
                        continue;
                    }
                    if (item.hasItemMeta()) {
                        if (item.getType() == Material.ARROW) continue;
                        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.SPONGE.getItemStack().getItemMeta().getDisplayName())) {
                            int amt = item.getAmount() - 1;
                            if (amt <= 0) {
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
                if (MySQLStatus.isSqlEnabled() && NanamiTct.playerStats != null) {
                    PlayerStat stat = NanamiTct.playerStats.getStat(p.getUniqueId());
                    if (stat != null) stat.setCountUsedItem(stat.getCountUsedItem() + 1);
                }
            }
        }
    }

    public void handleDeathCause(PlayerData data, EntityDamageEvent.DamageCause cause) {
        switch (cause) {
            case PROJECTILE: {
                if (data.getPlayer().getLastDamageCause() instanceof Projectile) {
                    Entity shooter = (Entity) ((Projectile) data.getPlayer().getLastDamageCause()).getShooter();

                    if (shooter != null) {
                        if (shooter instanceof Player) {
                            data.setKilledBy(new Killer(shooter.getName(), plugin.getGame().getReference().PLAYERDATA.get(shooter.getUniqueId()).getRole(), Killer.KillerCategory.PLAYER));
                        } else {
                            data.setKilledBy(new Killer(shooter.getName(), GameRoles.NONE, Killer.KillerCategory.AIR));
                        }
                    }

                    data.kill(TctDeathCause.KILL);
                }

                break;
            }
            case ENTITY_ATTACK:
            case ENTITY_SWEEP_ATTACK: {

                Player player = data.getPlayer().getKiller();

                if (player != null) {
                    data.setKilledBy(new Killer(player.getName(), plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId()).getRole(), Killer.KillerCategory.PLAYER));

                    if (MySQLStatus.isSqlEnabled() && NanamiTct.playerStats != null) {
                        PlayerStat stat = NanamiTct.playerStats.getStat(player.getUniqueId());
                        stat.setCountKill(stat.getCountKill() + 1);
                    }

                } else {
                    EntityDamageEvent force = player.getLastDamageCause();

                    if (force != null) {
                        data.setKilledBy(new Killer(force.getCause().name(), GameRoles.NONE, Killer.KillerCategory.AIR));
                    } else {
                        data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                    }
                }

                data.kill(TctDeathCause.KILL);
                break;
            }
            case ENTITY_EXPLOSION:
            case BLOCK_EXPLOSION: {
                data.setKilledBy(new Killer("TNT", GameRoles.WOLF, Killer.KillerCategory.TNT));

                data.kill(TctDeathCause.TNT);
                break;
            }
            case FALL: {
                data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));

                data.kill(TctDeathCause.FALL);
                break;
            }
            default: {
                data.setKilledBy(new Killer(cause.name(), GameRoles.NONE, Killer.KillerCategory.AIR));

                data.kill(TctDeathCause.AIR);
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();

        e.getDrops().clear();
        e.setDeathMessage(null);

        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());

        if (plugin.getGame().getReference().getGameState() == GameState.GAMING) {

            respawnLoc = plugin.getGame().getLocation();

            if (!data.isSpectator()) {
                p.getInventory().clear();

                if (e.getEntity().getLastDamageCause() != null) handleDeathCause(data, e.getEntity().getLastDamageCause().getCause());

                if (data.getKilledBy().getCategory() != Killer.KillerCategory.AIR) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_YOU_ARE_KILLED_BY.replaceAll("%PLAYER%", data.getKilledBy().getName()));
                }

                if (MySQLStatus.isSqlEnabled() && NanamiTct.playerStats != null) {
                    PlayerStat playerStat = NanamiTct.playerStats.getStat(p.getUniqueId());
                    playerStat.setCountDeath(playerStat.getCountDeath() + 1);
                }

                plugin.getGame().removeRemainingPlayers(data, true);

                if (NanamiTct.utilities.isEnded()) {
                    plugin.getGame().getTimer().cancel();
                } else {
                    //fireworks...
                    if (data.getWatcher() != null && data.getRole() == GameRoles.FOX) {
                        data.getWatcher().cancelCountFox();

                        NanamiTct.utilities.summonFoxFirework(p.getLocation());
                    }

                    //slowness
                    if (NanamiTct.utilities.hasSlownessGlassBottle(p)) {
                        Player killer = p.getKiller();
                        if (killer != null) {
                            NanamiTct.utilities.addSlownessPotion(killer);

                            if (MySQLStatus.isSqlEnabled() && NanamiTct.playerStats != null) {
                                PlayerStat stat = NanamiTct.playerStats.getStat(p.getUniqueId());
                                if (stat != null) stat.setCountUsedItem(stat.getCountUsedItem() + 1);
                            }
                        }
                    }
                }
            }
        } else {
            respawnLoc = e.getEntity().getLocation();
        }

        p.spigot().respawn();
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent e) {
        e.setRespawnLocation(respawnLoc);
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());

        if (data.isSpectator() && data.getRole() != GameRoles.SPEC && plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            e.getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_YOU_ARE_SPECTATOR_MODE);
        }
    }
}
