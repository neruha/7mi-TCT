package me.clockclap.tct.event;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.japanize.Japanizer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.omg.CORBA.UNSUPPORTED_POLICY;

import java.io.UnsupportedEncodingException;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) throws UnsupportedEncodingException {
        Player p = e.getPlayer();
        e.setCancelled(true);
        String result = Japanizer.japanize(e.getMessage());
        Bukkit.getServer().broadcastMessage(Reference.TCT_CHAT_FORMAT.replaceAll("%ROLE%", Reference.TCT_CHAT_ROLE_SPEC_P).replaceAll("%PLAYER%", p.getDisplayName()).replaceAll("%MESSAGE%", result));
    }

}
