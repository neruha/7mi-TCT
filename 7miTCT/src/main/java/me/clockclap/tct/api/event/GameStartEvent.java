package me.clockclap.tct.api.event;

import me.clockclap.tct.game.Game;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends GameEvent {

    private static final HandlerList handlers = new HandlerList();
    private Location location;

    public GameStartEvent(Game game, Location location) {
        super(game);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
