package me.clockclap.tct.game.role;

import org.bukkit.ChatColor;

public interface GameTeam {

    String getDisplayName();

    String getName();

    int getIndex();

    ChatColor getColor();

    GameTeam parent();

    void setDisplayName(String name);

    void setName(String name);

    void setIndex(int index);

    void setColor(ChatColor color);

}
