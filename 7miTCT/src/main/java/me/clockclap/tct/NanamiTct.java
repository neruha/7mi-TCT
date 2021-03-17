package me.clockclap.tct;

import me.clockclap.tct.api.TctConfiguration;
import me.clockclap.tct.api.Utilities;
import me.clockclap.tct.command.*;
import me.clockclap.tct.event.CancelHunger;
import me.clockclap.tct.event.ChatEvent;
import me.clockclap.tct.event.PlayerConnectionEvent;
import me.clockclap.tct.event.ProtectWorld;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.GameReference;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class NanamiTct extends JavaPlugin {

    public static NanamiTct plugin;
    public static Utilities utilities;

    private Game game;
    private TctConfiguration configuration;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        utilities = new Utilities(this);
        game = new Game(this);
        configuration = new TctConfiguration(this);
        try {
            configuration.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        plugin = this;
        getLogger().info("Starting up...");
        PluginManager pm = Bukkit.getServer().getPluginManager();

        //Register Events
        pm.registerEvents(new PlayerConnectionEvent(this), this);
        pm.registerEvents(new ChatEvent(this), this);
        pm.registerEvents(new ProtectWorld(this), this);
        pm.registerEvents(new CancelHunger(this), this);

        //Add Commands
        utilities.addCommand("abouttct", new CommandAboutTCT(this));
        utilities.addCommand("gmc", new CommandGameModeCreative());
        utilities.addCommand("gms", new CommandGameModeSurvival());
        utilities.addCommand("gmsall", new CommandGameModeSurvivalAll());
        utilities.addCommand("tctreload", new CommandTctReload(this));

        //Initialize player data
        for(Player p : Bukkit.getOnlinePlayers()) {
            getGame().getReference().PLAYERDATA.put(p.getName(), new TctPlayerData(GameRoles.SPEC, p.getName()));
        }
    }

    public Game getGame() {
        return this.game;
    }

    public TctConfiguration getTctConfig() {
        return this.configuration;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down...");
    }
}
