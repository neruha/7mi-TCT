package me.clockclap.tct;

import me.clockclap.tct.api.Reference;
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
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
        utilities.addCommand("barrier", new CommandBarrier(this));
        utilities.addCommand("start", new CommandStart(this));
        utilities.addCommand("stopgame", new CommandStopGame(this));

        //Register boss bar
        BossBar bar = Bukkit.getServer().createBossBar(Reference.TCT_BOSSBAR_FORMAT_WAITING, BarColor.RED, BarStyle.SOLID);
        bar.setVisible(true);
        bar.setProgress(1.0);
        this.game.setBar(bar);

        //Initialize player data
        if(Bukkit.getOnlinePlayers().size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                getGame().getReference().PLAYERDATA.put(p.getName(), new TctPlayerData(GameRoles.SPEC, p.getName()));
                bar.addPlayer(p);
            }
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

        //Unregister boss bar
        getGame().getBar().removeAll();
    }
}
