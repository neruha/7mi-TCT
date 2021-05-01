package me.clockclap.tct.api.event;

import me.clockclap.tct.game.Game;
import org.bukkit.event.Event;

public abstract class GameEvent extends Event {
    private final Game game;

    public GameEvent(final Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
