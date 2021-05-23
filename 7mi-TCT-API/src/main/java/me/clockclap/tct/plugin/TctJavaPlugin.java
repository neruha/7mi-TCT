package me.clockclap.tct.plugin;

import me.clockclap.tct.game.TctGame;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class TctJavaPlugin extends JavaPlugin {

    private TctGame game;

    public final TctGame getGame() {
        return game;
    }

    final void setGame(TctGame game) {
        this.game = game;
    }
}
