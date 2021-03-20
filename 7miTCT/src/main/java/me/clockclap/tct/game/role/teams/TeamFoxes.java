package me.clockclap.tct.game.role.teams;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameTeam;
import org.bukkit.ChatColor;

public class TeamFoxes implements GameTeam {

    private String displayName;
    private String name;
    private int index;
    private ChatColor color;

    public TeamFoxes() {
        this.name = "FOXES";
        this.displayName = Reference.TCT_TEAM_FOXES;
        this.index = 2;
        this.color = ChatColor.GOLD;
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
