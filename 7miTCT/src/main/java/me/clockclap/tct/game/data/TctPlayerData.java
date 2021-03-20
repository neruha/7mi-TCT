package me.clockclap.tct.game.data;

import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.boss.BossBar;

public class TctPlayerData implements PlayerData {

    private GameRole role;
    private GameRole co;
    private String name;
    private boolean spec;

    public TctPlayerData(GameRole role, String name) {
        this.role = role;
        this.name = name;
        this.co = GameRoles.NONE;
        this.spec = true;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public GameRole getCO() {
        return this.co;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isSpectator() {
        return this.spec;
    }

    @Override
    public void setRole(GameRole role) {
        this.role = role;
    }

    @Override
    public void setCO(GameRole role) {
        this.co = role;
    }

    @Override
    public void setSpectator(boolean bool) {
        this.spec = bool;
    }

}
