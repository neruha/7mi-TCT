package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TctGame;
import org.bukkit.event.Event;

public abstract class GameEvent extends Event {
    private final TctGame game;

    public GameEvent(final TctGame game) {
        this.game = game;
    }

    public TctGame getGame() {
        return game;
    }
}
