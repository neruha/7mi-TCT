package me.clockclap.tct.game.role;

import me.clockclap.tct.api.Reference;
import org.bukkit.ChatColor;

public abstract class TctRole implements GameRole {

    private String displayName;
    private String name;
    private String description;
    private int index;
    private ChatColor color;
    private GameTeam team;

    public TctRole(int index) {
        this(index, "", "", "", ChatColor.RESET, GameTeams.NONE);
    }

    public TctRole(int index, String name) {
        this(index, name, "", "", ChatColor.RESET, GameTeams.NONE);
    }

    public TctRole(int index, String name, String displayName) {
        this(index, name, displayName, "", ChatColor.RESET, GameTeams.NONE);
    }

    public TctRole(int index, String name, String displayName, String description) {
        this(index, name, displayName, description, ChatColor.RESET, GameTeams.NONE);
    }

    public TctRole(int index, String name, String displayName, String description, ChatColor color) {
        this(index, name, displayName, description, color, GameTeams.NONE);
    }

    public TctRole(int index, String name, String displayName, String description, ChatColor color, GameTeam team) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.index = index;
        this.color = color;
        this.team = team;
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
    public String getDescription() {
        return this.description;
    }

    @Override
    public ChatColor getColor() {
        return this.color;
    }

    @Override
    public GameTeam getTeam() {
        return this.team;
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

    @Override
    public void setTeam(GameTeam team) {
        this.team = team;
    }

}
