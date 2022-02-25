package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TCTGame;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GamePreStartEvent extends GameEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Location location;
    private String cancelMessage;

    public GamePreStartEvent(TCTGame game, Location location, String cancelMessage) {
        super(game);
        this.location = location;
        this.cancelMessage = cancelMessage;
    }

    public void setCancelMessage(String message) {
        this.cancelMessage = message;
    }

    public String getCancelMessage() {
        return cancelMessage;
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

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
