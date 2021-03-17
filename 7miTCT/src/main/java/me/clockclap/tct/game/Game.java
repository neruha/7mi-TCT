package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;

public class Game {

    private NanamiTct plugin;
    private GameReference reference;

    public Game(NanamiTct plugin) {
        this.plugin = plugin;
        this.reference = new GameReference(plugin);
    }

    public NanamiTct getPlugin() {
        return this.plugin;
    }

    public GameReference getReference() {
        return this.reference;
    }

    public void setReference(GameReference reference) {
        this.reference = reference;
    }

}
