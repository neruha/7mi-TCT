package me.clockclap.tct.game.data;

import org.bukkit.Location;

public interface CustomProjectileData extends EntityData {

    public Location getLocation();

    public void startTimer();

    public void cancelTimer();

}
