package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TctGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class GamePlayerEvent extends PlayerEvent {
    private static HandlerList handlers;

    private TctGame game;

    public GamePlayerEvent(TctGame game, Player who) {
        super(who);
        this.game = game;
    }

    public TctGame getGame() {
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
