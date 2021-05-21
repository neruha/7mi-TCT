package me.clockclap.tct.game;

public enum GameState {

    WAITING(0, "waiting"),
    STARTING(1, "starting"),
    GAMING(2, "gaming"),
    ENDING(3, "ending");

    private final int index;
    private final String id;
    private final String toString;

    private GameState(int index, String id) {
        this.index = index;
        this.id = id;
        this.toString = "(" + index + ": " + id + ")";
    }

    public int getIndex() {
        return this.index;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.toString;
    }
}
