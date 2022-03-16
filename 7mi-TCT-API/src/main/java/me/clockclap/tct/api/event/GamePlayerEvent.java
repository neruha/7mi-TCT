package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TCTGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class GamePlayerEvent extends PlayerEvent {
    private static HandlerList handlers;

    private TCTGame game;

    public GamePlayerEvent(TCTGame game, Player who) {
        super(who);
        this.game = game;
    }

    public TCTGame getGame() {
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
