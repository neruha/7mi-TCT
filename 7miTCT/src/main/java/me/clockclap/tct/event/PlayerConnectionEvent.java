package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
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
        e.setJoinMessage(Reference.TCT_CHAT_JOIN_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0);
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_1);
        String gameState = "";
        if(plugin.getGameReference().getGameState() == GameState.WAITING) {
            gameState = "Waiting";
        } else if(plugin.getGameReference().getGameState() == GameState.GAMING) {
            gameState = "Gaming";
        }
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_GAME_STATE.replaceAll("%STATE%", gameState));
        if(gameState.equalsIgnoreCase("Waiting")) {
            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_PLEASE_WAIT);
        } else if(gameState.equalsIgnoreCase("Gaming")) {
            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_ALREADY_STARTED);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Reference.TCT_CHAT_QUIT_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
    }

}
