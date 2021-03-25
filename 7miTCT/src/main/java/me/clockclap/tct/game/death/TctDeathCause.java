package me.clockclap.tct.game.death;

import me.clockclap.tct.api.Reference;

public enum TctDeathCause {

    AIR(0, Reference.TCT_DEATHCAUSE_AIR),
    KILL(1, Reference.TCT_DEATHCAUSE_KILL),
    TNT(2, Reference.TCT_DEATHCAUSE_TNT),
    FALL(3, Reference.TCT_DEATHCAUSE_FALL),
    LOST_CONNECTION(4, Reference.TCT_DEATHCAUSE_LOST_CONNECTION);

    private final int index;
    private final String name;

    private TctDeathCause(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
