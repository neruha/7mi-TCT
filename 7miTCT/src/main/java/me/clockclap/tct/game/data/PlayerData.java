package me.clockclap.tct.game.data;

import me.clockclap.tct.game.role.GameRole;

public interface PlayerData {

    public GameRole getRole();

    public GameRole getCO();

    public String getName();

    public void setRole(GameRole role);

    public void setCO(GameRole role);

}
