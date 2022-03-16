package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TCTGame;
import org.bukkit.event.Event;

public abstract class GameEvent extends Event {
    private final TCTGame game;

    public GameEvent(final TCTGame game) {
        this.game = game;
    }

    public TCTGame getGame() {
        return game;
    }
}
