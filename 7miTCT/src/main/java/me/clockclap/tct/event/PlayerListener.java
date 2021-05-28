package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.TctUtilities;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.data.TctPlayerStat;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.GameTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    private NanamiTct plugin;

    public PlayerListener(NanamiTct plugin) {
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
        plugin.getGame().getReference().PLAYERDATA.put(p.getUniqueId(), data);
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0.replaceAll("%VERSION%", plugin.getDescription().getVersion()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_1);
        if(MySQLStatus.isSqlEnabled() && NanamiTct.sqlConnection != null && NanamiTct.playerStats != null && NanamiTct.sqlConnection.getConnection() != null) {
            PlayerStat stat = new TctPlayerStat(p.getUniqueId());
            try {
                NanamiTct.playerStats.insert(stat);
            } catch (SQLException ex) {
                ex.printStackTrace();
                MySQLStatus.setSqlEnabled(false);
            }
        }
        String gameState,message = "";
        boolean isAdmin = data.getProfile().isAdmin();
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            gameState = Reference.TCT_CHAT_STATE_PLAYING;
            TctUtilities utilities = NanamiTct.utilities;
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
            TctUtilities utilities = NanamiTct.utilities;
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
            TctUtilities utilities = NanamiTct.utilities;
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
        if(!MySQLStatus.isSqlEnabled()) p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* データベースに接続できなかったため統計の表示、記録はできません *");
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
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
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
        plugin.getGame().getReference().PLAYERDATA.remove(p.getUniqueId());
    }

    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            PlayerData data = NanamiTctApi.utilities.getPlayerData(p);
            if(data != null && data.isTeleporting()) e.setCancelled(true);
        }
    }

}
