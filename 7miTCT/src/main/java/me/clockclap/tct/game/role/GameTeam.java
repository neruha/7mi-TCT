package me.clockclap.tct.game.role;

import org.bukkit.ChatColor;

public interface GameTeam {

    public String getDisplayName();

    public String getName();

    public int getIndex();

    public ChatColor getColor();

    public void setDisplayName(String name);

    public void setName(String name);

    public void setIndex(int index);

    public void setColor(ChatColor color);

}
