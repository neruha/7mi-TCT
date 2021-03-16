package me.clockclap.tct.game.role.teams;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameTeam;
import org.bukkit.ChatColor;

public class TeamVillager implements GameTeam {

    private String displayName;
    private String name;
    private int index;
    private ChatColor color;

    public TeamVillager() {
        this.name = "VILLAGERS";
        this.displayName = Reference.TCT_TEAM_VILLAGERS;
        this.index = 0;
        this.color = ChatColor.GREEN;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public ChatColor getColor() {
        return this.color;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void setColor(ChatColor color) {
        this.color = color;
    }
}
