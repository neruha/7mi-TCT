package me.clockclap.tct.api;

import me.clockclap.tct.game.TCTGame;

public abstract class TctCooldownType implements CooldownType {

    private final TCTGame game;
    private final String name;
    private final int defaultCooltime;

    public TctCooldownType(TCTGame game, String name, int defaultCooltime) {
        this.game = game;
        this.name = name;
        this.defaultCooltime = defaultCooltime;
    }

    public TCTGame getGame() {
        return this.game;
    }

    public String getName() {
        return this.name;
    }

    public int getDefaultCooltime() {
        return this.defaultCooltime;
    }

}
