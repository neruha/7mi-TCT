package me.clockclap.tct.api;

import me.clockclap.tct.game.Game;

public abstract class TctCooldownType implements CooldownType {

    private final Game game;
    private final String name;
    private final int defaultCooltime;

    public TctCooldownType(Game game, String name, int defaultCooltime) {
        this.game = game;
        this.name = name;
        this.defaultCooltime = defaultCooltime;
    }

    public Game getGame() {
        return this.game;
    }

    public String getName() {
        return this.name;
    }

    public int getDefaultCooltime() {
        return this.defaultCooltime;
    }

}
