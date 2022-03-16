package me.clockclap.tct.game.role;

import org.bukkit.ChatColor;

public interface GameRole {

    String getDisplayName();

    String getName();

    int getIndex();

    String getDescription();

    ChatColor getColor();

    GameTeam getTeam();

    void setDisplayName(String name);

    void setName(String name);

    void setIndex(int index);

    void setColor(ChatColor color);

    void setTeam(GameTeam team);

}
