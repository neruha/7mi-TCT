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
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
            if(plugin.getGame().getReference().PLAYERDATA.get(p.getName()).isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            e.getDrops().clear();
            Location loc = p.getLocation();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getName());
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
                        if (d.getRole().getTeam() == GameTeams.FOXES) {
                            foxes.add(d);
                        }
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
                    if (wolves.size() > 0 && villagers.size() <= 0) {
                        plugin.getGame().getTimer().cancel();
                        if (foxes.size() > 0) {
                            plugin.getGame().stop(GameTeams.FOXES);
                            return;
                        }
                        plugin.getGame().stop(GameTeams.WOLVES);
                        return;
                    }
                    if (villagers.size() <= 0 && wolves.size() <= 0) {
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
                            data.setKilledBy(new Killer(p.getKiller(), plugin.getGame().getReference().PLAYERDATA.get(p.getKiller().getName()).getRole()));
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
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(respawnLoc);
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getName());
        if(data.isSpectator() && data.getRole() != GameRoles.SPEC && plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if(data.getKilledBy().getCategory() != Killer.KillerCategory.AIR) {
                e.getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_YOU_ARE_KILLED_BY.replaceAll("%PLAYER%", data.getKilledBy().getName()));
            }
        }
    }

}
