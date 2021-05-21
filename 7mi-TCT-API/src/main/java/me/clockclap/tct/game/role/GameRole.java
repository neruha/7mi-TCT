package me.clockclap.tct.game.role;

import org.bukkit.ChatColor;

public interface GameRole {

    public String getDisplayName();

    public String getName();

    public int getIndex();

    public String getDescription();

    public ChatColor getColor();

    public GameTeam getTeam();

    public void setDisplayName(String name);

    public void setName(String name);

    public void setIndex(int index);

    public void setColor(ChatColor color);

    public void setTeam(GameTeam team);

}
