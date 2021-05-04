package me.clockclap.tct.api.event;

import me.clockclap.tct.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class GamePlayerEvent extends PlayerEvent {
    private static HandlerList handlers;

    private Game game;

    public GamePlayerEvent(Game game, Player who) {
        super(who);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
