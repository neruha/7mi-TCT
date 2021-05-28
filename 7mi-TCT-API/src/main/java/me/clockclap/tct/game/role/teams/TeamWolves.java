package me.clockclap.tct.game.role.teams;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameTeam;
import org.bukkit.ChatColor;

public class TeamWolves implements GameTeam {

    private String displayName;
    private String name;
    private int index;
    private ChatColor color;
    private GameTeam parent;

    public TeamWolves() {
        this.name = "WOLVES";
        this.displayName = Reference.TCT_TEAM_WOLVES;
        this.index = 1;
        this.color = ChatColor.RED;
        this.parent = this;
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
    public GameTeam parent() {
        return this.parent;
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
