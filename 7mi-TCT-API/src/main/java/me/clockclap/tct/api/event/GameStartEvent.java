package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TctGame;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends GameEvent {

    private static final HandlerList handlers = new HandlerList();
    private Location location;

    public GameStartEvent(TctGame game, Location location) {
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
