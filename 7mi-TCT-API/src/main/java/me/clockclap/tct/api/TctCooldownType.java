package me.clockclap.tct.api;

import me.clockclap.tct.game.TctGame;

public abstract class TctCooldownType implements CooldownType {

    private final TctGame game;
    private final String name;
    private final int defaultCooltime;

    public TctCooldownType(TctGame game, String name, int defaultCooltime) {
        this.game = game;
        this.name = name;
        this.defaultCooltime = defaultCooltime;
    }

    public TctGame getGame() {
        return this.game;
    }

    public String getName() {
        return this.name;
    }

    public int getDefaultCooltime() {
        return this.defaultCooltime;
    }

}
