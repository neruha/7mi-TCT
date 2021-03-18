package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEvent implements Listener {

    private NanamiTct plugin;

    public PlayerConnectionEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.getGame().getBar().addPlayer(p);
        PlayerData data = new TctPlayerData(GameRoles.SPEC, p.getName());
        plugin.getGame().getReference().PLAYERDATA.put(p.getName(), data);
        e.setJoinMessage(Reference.TCT_CHAT_JOIN_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0.replaceAll("%VERSION%", plugin.getDescription().getVersion()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_1);
        String gameState = "";
        String message = "";
        boolean isAdmin = false;
        if(plugin.getTctConfig().getConfig().getStringList("admin").contains("op")) {
            if(p.isOp()) {
                isAdmin = true;
            }
        }
        if(isAdmin == false) {
            for (String str : plugin.getTctConfig().getConfig().getStringList("admin")) {
                if (p.getName().equalsIgnoreCase(str)) {
                    isAdmin = true;
                    break;
                }
            }
        }
        if(plugin.getGame().getReference().getGameState() == GameState.WAITING) {
            gameState = "Waiting";
            if(isAdmin == true) {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_START;
            } else {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_WAIT;
            }
        } else if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            gameState = "Gaming";
            message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_ALREADY_STARTED;
        } else {
            gameState = "Waiting";
            if(isAdmin == true) {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_START;
            } else {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_WAIT;
            }
        }
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_GAME_STATE.replaceAll("%STATE%", gameState));
        p.sendMessage(message);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {

        }
        plugin.getGame().getReference().PLAYERDATA.remove(p.getName());
        e.setQuitMessage(Reference.TCT_CHAT_QUIT_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
    }

}
