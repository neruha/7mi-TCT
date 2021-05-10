package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.data.CustomProjectileData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.death.DeadBody;;
import org.bukkit.entity.Projectile;

import java.lang.reflect.Array;
import java.util.*;

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
    public Map<UUID, PlayerData> PLAYERDATA = new HashMap<>();
    public Map<Projectile, CustomProjectileData> PROJECTILEDATA = new HashMap<>();
    public List<DeadBody> DEADBODIES = new ArrayList<>();

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

}
