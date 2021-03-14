package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;

public class GameReference {

    private NanamiTct plugin;

    public GameReference(NanamiTct plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        this.gameState = GameState.WAITING;
    }

    private GameState gameState;

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

}
