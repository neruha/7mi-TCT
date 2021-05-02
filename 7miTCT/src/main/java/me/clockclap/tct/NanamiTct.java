package me.clockclap.tct;

import com.google.common.base.Charsets;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.TctConfiguration;
import me.clockclap.tct.api.Utilities;
import me.clockclap.tct.api.event.ArmorListener;
import me.clockclap.tct.command.*;
import me.clockclap.tct.event.*;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.GameReference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.role.CustomRoles;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.CustomTeams;
import me.clockclap.tct.inventory.CustomInventory;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class NanamiTct extends JavaPlugin {

    public static NanamiTct plugin;
    public static Utilities utilities;
    public static CustomTeams teamRegisterer;
    public static CustomRoles roleRegisterer;

    private Game game;
    private TctConfiguration configuration;
    private CustomInventory customInventory;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        utilities = new Utilities(this);
        teamRegisterer = new CustomTeams();
        roleRegisterer = new CustomRoles();
        game = new Game(this);
        configuration = new TctConfiguration(this);
        try {
            configuration.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLogger().info("Starting up...");
        PluginManager pm = Bukkit.getServer().getPluginManager();

        // Register Events
        pm.registerEvents(new PlayerConnectionEvent(this), this);
        pm.registerEvents(new ChatEvent(this), this);
        pm.registerEvents(new CancelHunger(this), this);
        pm.registerEvents(new ItemEvent(this), this);
        pm.registerEvents(new BlockEvent(this), this);
        pm.registerEvents(new DamageEvent(this), this);
        pm.registerEvents(new InventoryEvent(this), this);
        pm.registerEvents(new ArmorListener(Reference.TCT_BLOCKED), this);

        // Add Commands
        utilities.addCommand("abouttct", new CommandAboutTCT(this));
        utilities.addCommand("gmc", new CommandGameModeCreative());
        utilities.addCommand("gms", new CommandGameModeSurvival());
        utilities.addCommand("gmsall", new CommandGameModeSurvivalAll());
        utilities.addCommand("tctreload", new CommandTctReload(this));
        utilities.addCommand("barrier", new CommandBarrier(this));
        utilities.addCommand("start", new CommandStart(this));
        utilities.addCommand("startloc", new CommandStartLoc(this));
        utilities.addCommand("stopgame", new CommandStopGame(this));
        utilities.addCommand("item", new CommandItem(this));
        utilities.addCommand("shop", new CommandShop(this));

        // Register items
        CustomItems.register();

        // Initialize TCT log
        game.getLog().initialize();

        // Register boss bar
        BossBar bar = Bukkit.getServer().createBossBar(Reference.TCT_BOSSBAR_FORMAT_WAITING, BarColor.RED, BarStyle.SOLID);
        bar.setVisible(true);
        bar.setProgress(1.0);
        this.game.setBar(bar);

        // Initialize player data
        if(Bukkit.getOnlinePlayers().size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(p != null) {
                    String name = utilities.resetColor(p.getName());
                    PlayerData data = new TctPlayerData(this, GameRoles.SPEC, name);
                    PlayerWatcher watcher = new PlayerWatcher(plugin.getGame(), p);
                    data.setSpectator(true);
                    data.setWatcher(watcher);
                    data.getWatcher().startWatch();
                    boolean isAdmin = false;
                    FileConfiguration config = getTctConfig().getConfig();
                    if (config.getStringList("admin").contains(name)) {
                        isAdmin = true;
                    } else if (config.getStringList("admin").contains("op") && p.isOp()) {
                        isAdmin = true;
                    }
                    data.getProfile().modify().setBoolean("admin", isAdmin).save();
                    getGame().getReference().PLAYERDATA.put(name, data);
                    p.setFoodLevel(20);
                    p.setPlayerListName(ChatColor.GREEN + name);
                    bar.addPlayer(p);
                    Utilities utilities = NanamiTct.utilities;
                    utilities.modifyName(p, ChatColor.GREEN + name);
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        if (pl != null) {
                            pl.hidePlayer(plugin, p);
                            pl.showPlayer(plugin, p);
                        }
                    }
                    utilities.modifyName(p, name);
                    data.setSponge(false);
                    data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                }
            }
        }

        customInventory = new CustomInventory(game);
        customInventory.initialize();

    }

    public Game getGame() {
        return this.game;
    }

    public TctConfiguration getTctConfig() {
        return this.configuration;
    }

    public CustomInventory getCustomInventory() {
        return this.customInventory;
    }

    private void reload() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(plugin.getTctConfig().getConfigFile());

        final InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            plugin.getTctConfig().getConfig().load(plugin.getTctConfig().getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p != null) {
                String name = NanamiTct.utilities.resetColor(p.getName());
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(name);
                if(data == null) {
                    continue;
                }
                boolean isAdmin = false;
                FileConfiguration config = plugin.getTctConfig().getConfig();
                if(config.getStringList("admin").contains(name)) {
                    isAdmin = true;
                } else if(config.getStringList("admin").contains("op") && p.isOp()) {
                    isAdmin = true;
                }
                data.getProfile().modify().setBoolean("admin", isAdmin).save();
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down...");

        // Unregister items
        CustomItems.unregister();

        // Unregister boss bar
        getGame().getBar().removeAll();
    }
}
