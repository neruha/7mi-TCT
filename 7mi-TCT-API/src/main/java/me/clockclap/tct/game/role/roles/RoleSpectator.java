package me.clockclap.tct.game.role.roles;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameTeam;
import me.clockclap.tct.game.role.GameTeams;
import org.bukkit.ChatColor;

public class RoleSpectator implements GameRole {

    private String displayName;
    private String name;
    private String description;
    private int index;
    private ChatColor color;
    private GameTeam team;

    public RoleSpectator() {
        this.name = "SPEC";
        this.displayName = Reference.TCT_TEAM_SPEC;
        this.description = "";
        this.index = -1;
        this.color = ChatColor.GREEN;
        this.team = GameTeams.SPEC;

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
