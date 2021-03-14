package me.clockclap.tct.game;

public enum GameState {

    WAITING(0, "waiting"),
    STARTING(1, "starting"),
    GAMING(2, "gaming"),
    ENDING(3, "ending");

    private final int gameStateCode;
    private final String toString;

    private GameState(int code, String id) {
        this.gameStateCode = code;
        this.toString = id;
    }

    public int getCode() {
        return this.gameStateCode;
    }

    @Override
    public String toString() {
        return this.toString;
    }
}
