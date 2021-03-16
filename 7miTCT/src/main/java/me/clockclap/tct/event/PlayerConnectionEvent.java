package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.role.GameRoles;
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
        PlayerData data = new TctPlayerData(GameRoles.SPEC, p.getName());
        plugin.getGameReference().PLAYERDATA.put(p.getName(), data);
        e.setJoinMessage(Reference.TCT_CHAT_JOIN_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0);
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_1);
        String gameState = "";
        String message = "";
        if(plugin.getGameReference().getGameState() == GameState.WAITING) {
            gameState = "Waiting";
            if(p.isOp()) {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_START;
            } else {
                message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_WAIT;
            }
        } else if(plugin.getGameReference().getGameState() == GameState.GAMING) {
            gameState = "Gaming";
            message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_ALREADY_STARTED;
        } else {
            message = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_WAIT;
            gameState = "Waiting";
        }
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_GAME_STATE.replaceAll("%STATE%", gameState));
        p.sendMessage(message);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(plugin.getGameReference().getGameState() == GameState.GAMING) {

        }
        plugin.getGameReference().PLAYERDATA.remove(p.getName());
        e.setQuitMessage(Reference.TCT_CHAT_QUIT_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
    }

}
