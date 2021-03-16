package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.data.PlayerData;
import org.bukkit.craftbukkit.libs.jline.console.KeyMap;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, PlayerData> PLAYERDATA = new HashMap<>();

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

}
