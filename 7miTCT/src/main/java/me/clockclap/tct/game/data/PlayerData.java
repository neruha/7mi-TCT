package me.clockclap.tct.game.data;

import me.clockclap.tct.game.role.GameRole;
import org.bukkit.boss.BossBar;

public interface PlayerData {

    public GameRole getRole();

    public GameRole getCO();

    public String getName();

    public boolean isSpectator();

    public void setRole(GameRole role);

    public void setCO(GameRole role);

    public void setSpectator(boolean bool);

}
