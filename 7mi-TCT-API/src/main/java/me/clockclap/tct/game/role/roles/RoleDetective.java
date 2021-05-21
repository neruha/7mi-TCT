package me.clockclap.tct.game.role.roles;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameTeam;
import me.clockclap.tct.game.role.GameTeams;
import org.bukkit.ChatColor;

public class RoleDetective implements GameRole {

    private String displayName;
    private String name;
    private String description;
    private int index;
    private ChatColor color;
    private GameTeam team;

    public RoleDetective() {
        this.name = "DETECTIVE";
        this.displayName = Reference.TCT_ROLE_DETECTIVE;
        this.description = Reference.TCT_CHAT_ROLE_DESCRIPTION_DETECTIVE;
        this.index = 2;
        this.color = ChatColor.AQUA;
        this.team = GameTeams.VILLAGERS;

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
