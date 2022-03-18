package me.clockclap.tct.game.data;

import org.bukkit.Location;

public interface CustomProjectileData extends EntityData {

    Location getLocation();

    void startTimer();

    void cancelTimer();

}
