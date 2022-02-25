package me.clockclap.tct.plugin;

import me.clockclap.tct.game.TCTGame;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class TctJavaPlugin extends JavaPlugin {

    private TCTGame game;

    public final TCTGame getGame() {
        return game;
    }

    final void setGame(TCTGame game) {
        this.game = game;
    }
}
