package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.japanize.Japanizer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.UnsupportedEncodingException;

public class ChatListener implements Listener {

    private final NanamiTct plugin;

    public ChatListener(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) throws UnsupportedEncodingException {
        e.setCancelled(true);
        final boolean japanize = plugin.getTctConfig().getChat().getBoolean("japanize", true);
        if (japanize) {
            String format = plugin.getTctConfig().getChat().getString("japanize_format", Reference.JAPANIZE_FORMAT);
            handle(e.getPlayer(), format.replaceAll("%JAPANIZE%", Japanizer.japanizeColorCode(e.getMessage())).replaceAll("%MESSAGE%", e.getMessage()));
        } else {
            handle(e.getPlayer(), e.getMessage());
        }
    }

    public void handle(Player player, String message) {
        String role_chatprefix;

        GameRole role = plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId()).getRole();
        boolean visible = true;
        GameRole co = plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId()).getCO();
        if (role == GameRoles.SPEC || plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId()).isSpectator()) {
            role_chatprefix = Reference.TCT_CHAT_ROLE_SPEC_P;
            if (plugin.getGame().getReference().getGameState() == GameState.GAMING) {
                visible = false;
            }
        } else {
            if (co == GameRoles.VILLAGER) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_VILLAGER_P;
            } else if (co == GameRoles.HEALER) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_HEALER_P;
            } else if (co == GameRoles.DETECTIVE) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_DETECTIVE_P;
            } else if (co == GameRoles.WOLF) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_WOLF_P;
            } else if (co == GameRoles.FANATIC) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_FANATIC_P;
            } else if (co == GameRoles.FOX) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_FOX_P;
            } else if (co == GameRoles.IMMORAL) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_IMMORAL_P;
            } else if (co == GameRoles.CONFIRM_DETECTIVE) {
                role_chatprefix = Reference.TCT_CHAT_ROLE_DETECTIVE;
            } else {
                role_chatprefix = Reference.TCT_CHAT_ROLE_CO_NONE_P;
            }
        }
        if (visible) {
            String format = plugin.getTctConfig().getChat().getString("format", Reference.TCT_CHAT_FORMAT);
            format = ChatColor.translateAlternateColorCodes('&', format);
            Bukkit.broadcastMessage(format.replaceAll("%ROLE%", role_chatprefix).replaceAll("%PLAYER%", player.getDisplayName()).replaceAll("%MESSAGE%", message));
        } else {
            for (Player q : Bukkit.getOnlinePlayers()) {
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(q.getUniqueId());
                if (data.getRole() == GameRoles.SPEC || data.isSpectator()) {
                    String format = plugin.getTctConfig().getChat().getString("format", Reference.TCT_CHAT_FORMAT);
                    format = ChatColor.translateAlternateColorCodes('&', format);
                    q.sendMessage(format.replaceAll("%ROLE%", role_chatprefix).replaceAll("%PLAYER%", player.getDisplayName()).replaceAll("%MESSAGE%", message));
                }
            }
        }
    }
}