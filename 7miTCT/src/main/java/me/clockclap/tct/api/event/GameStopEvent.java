package me.clockclap.tct.api.event;

import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.role.GameTeam;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class GameStopEvent extends GameEvent {

    private static final HandlerList handlers = new HandlerList();
    private Location location;
    private GameTeam winners;

    public GameStopEvent(Game game, Location location, GameTeam winners) {
        super(game);
        this.location = location;
        this.winners = winners;
    }

    public Location getLocation() {
        return location;
    }

    public GameTeam getWinners() {
        return winners;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
