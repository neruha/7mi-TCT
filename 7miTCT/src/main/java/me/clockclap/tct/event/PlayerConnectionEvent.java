package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.Utilities;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.GameTeams;
import net.minecraft.server.v1_12_R1.CommandSay;
import net.minecraft.server.v1_12_R1.ICommandListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerConnectionEvent implements Listener {

    private NanamiTct plugin;

    public PlayerConnectionEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(Reference.TCT_CHAT_JOIN_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
        plugin.getGame().getBar().addPlayer(p);
        PlayerData data = new TctPlayerData(plugin, GameRoles.SPEC, NanamiTct.utilities.resetColor(p.getName()));
        PlayerWatcher watcher = new PlayerWatcher(plugin.getGame(), p);
        data.setSpectator(true);
        data.setWatcher(watcher);
        data.getWatcher().startWatch();
        plugin.getGame().getReference().PLAYERDATA.put(NanamiTct.utilities.resetColor(p.getName()), data);
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0.replaceAll("%VERSION%", plugin.getDescription().getVersion()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_1);
        String gameState = "";
        String message = "";
        boolean isAdmin = data.getProfile().isAdmin();
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            gameState = Reference.TCT_CHAT_STATE_PLAYING;
            Utilities utilities = NanamiTct.utilities;
            for(Player pl : Bukkit.getOnlinePlayers()) {
                NanamiTct.utilities.hidePlayer(p, pl);
                NanamiTct.utilities.showPlayer(p, pl);
            }
            utilities.modifyName(p, utilities.resetColor(p.getName()));
            p.setPlayerListName("");
            p.setGameMode(GameMode.SPECTATOR);
            p.teleport(plugin.getGame().getLocation());
            message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_ALREADY_STARTED;
        } else if(plugin.getGame().getReference().getGameState() == GameState.STARTING) {
            gameState = Reference.TCT_CHAT_STATE_PREGAMING;
            Utilities utilities = NanamiTct.utilities;
            utilities.modifyName(p, ChatColor.GREEN + utilities.resetColor(p.getName()));
            for(Player pl : Bukkit.getOnlinePlayers()) {
                utilities.hidePlayer(p, pl);
                utilities.showPlayer(p, pl);
            }
            utilities.reloadPlayer();
            utilities.modifyName(p, utilities.resetColor(p.getName()));
            p.setPlayerListName("");
            p.teleport(plugin.getGame().getLocation());
            message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_READY_TIME;
        } else {
            gameState = Reference.TCT_CHAT_STATE_WAITING;
            Utilities utilities = NanamiTct.utilities;
            utilities.modifyName(p, ChatColor.GREEN + utilities.resetColor(p.getName()));
            for(Player pl : Bukkit.getOnlinePlayers()) {
                utilities.hidePlayer(p, pl);
                utilities.showPlayer(p, pl);
            }
            utilities.reloadPlayer();
            utilities.modifyName(p, utilities.resetColor(p.getName()));
            p.setPlayerListName(ChatColor.GREEN + NanamiTct.utilities.resetColor(p.getName()));
            p.setDisplayName(utilities.resetColor(p.getName()));
            if(isAdmin) {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_START;
            } else {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_WAIT;
            }
        }
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_GAME_STATE.replaceAll("%STATE%", gameState));
        p.sendMessage(message);
        for(Player pl : Bukkit.getOnlinePlayers()) {
            NanamiTct.utilities.hidePlayer(pl, p);
            new BukkitRunnable() {
                @Override
                public void run() {
                    NanamiTct.utilities.showPlayer(pl, p);
                }
            }.runTaskLater(plugin, 2);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Reference.TCT_CHAT_QUIT_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
        if(data.getWatcher() != null) {
            data.getWatcher().cancelPlayerWatcher();
        }
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if(!data.isSpectator()) {
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
                if(data.getRole() == GameRoles.FOX && data.getWatcher() != null) {
                    data.getWatcher().cancelCountFox();
                }
                data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                data.kill(TctDeathCause.LOST_CONNECTION);
            }
        }
        plugin.getGame().getReference().PLAYERDATA.remove(NanamiTct.utilities.resetColor(p.getName()));
    }

}
