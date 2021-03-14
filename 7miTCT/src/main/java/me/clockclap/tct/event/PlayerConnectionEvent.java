package me.clockclap.tct.event;

import me.clockclap.tct.api.Reference;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(Reference.TCT_CHAT_JOIN_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_0);
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_1);
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_JOIN_MESSAGE_2);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Reference.TCT_CHAT_QUIT_MESSAGE.replaceAll("%PLAYER%",p.getDisplayName()));
    }

}
