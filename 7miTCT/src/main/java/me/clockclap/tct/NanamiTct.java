package me.clockclap.tct;

import com.google.common.base.Charsets;
import me.clockclap.tct.api.*;
import me.clockclap.tct.api.event.ArmorListener;
import me.clockclap.tct.api.sql.MySQLConnection;
import me.clockclap.tct.api.sql.MySQLPlayerStats;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.command.*;
import me.clockclap.tct.event.*;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.TctGame;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.data.TctPlayerStat;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.role.CustomRoles;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomTeams;
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
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NanamiTct extends JavaPlugin {

    public static NanamiTct plugin;
    public static TctUtilities utilities;
    public static CustomTeams teamRegisterer;
    public static CustomRoles roleRegisterer;
    public static MySQLConnection sqlConnection;
    public static MySQLPlayerStats playerStats;
    public static boolean isLoaded = false;

    private TctGame game;
    private ITctConfiguration configuration;
    private CustomInventory customInventory;
    private Plugin[] loadedPlugins;
    private SimplePluginManager pluginManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        NanamiTctApi.plugin = plugin;
        pluginManager = (SimplePluginManager) Bukkit.getServer().getPluginManager();
        utilities = new TctUtilities(this);
        NanamiTctApi.utilities = utilities;
        teamRegisterer = new CustomTeams();
        NanamiTctApi.teamRegistry = teamRegisterer;
        roleRegisterer = new CustomRoles();
        NanamiTctApi.roleRegistry = roleRegisterer;
        game = new Game(this);
        NanamiTctApi.game = game;
        configuration = new TctConfiguration(this);
        try {
            configuration.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NanamiTctApi.config = configuration;
        sqlConnection = new MySQLConnection(configuration.getConfig().getString("mysql.hostname", "localhost"),
                configuration.getConfig().getInt("mysql.port", 3306),
                configuration.getConfig().getString("mysql.database", ""),
                configuration.getConfig().getString("mysql.username", ""),
                configuration.getConfig().getString("mysql.password", ""),
                configuration.getConfig().getString("mysql.option", "?allowPublicKeyRetrieval=true&useSSL=false"));

        try {
            sqlConnection.openConnection();
            MySQLStatus.setSqlEnabled(true);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
        NanamiTctApi.connection = sqlConnection;

        if(MySQLStatus.isSqlEnabled()) {
            playerStats = new MySQLPlayerStats(sqlConnection, game);
            try {
                playerStats.createTable();
            } catch (SQLException throwables) {
                MySQLStatus.setSqlEnabled(false);
                throwables.printStackTrace();
            }
        }
        NanamiTctApi.playerStats = playerStats;

        getLogger().info("Starting up...");

        // Register Events
        pluginManager.registerEvents(new PlayerConnectionEvent(this), this);
        pluginManager.registerEvents(new ChatEvent(this), this);
        //pluginManager.registerEvents(new CancelHunger(this), this);
        pluginManager.registerEvents(new ItemEvent(this), this);
        pluginManager.registerEvents(new BlockEvent(this), this);
        pluginManager.registerEvents(new DamageEvent(this), this);
        pluginManager.registerEvents(new InventoryEvent(this), this);
        pluginManager.registerEvents(new ArmorListener(Reference.TCT_BLOCKED), this);

        // Add Commands
        utilities.addCommand("abouttct", getName(), "", "7mi-TCTの情報を知ることができます。", new ArrayList<>(), new CommandAboutTCT(this));
        utilities.addCommand("gmc", getName(), "", "自身のゲームモードをクリエイティブモードに変更します。", new ArrayList<>(), new CommandGameModeCreative());
        utilities.addCommand("gms", getName(), "", "自身のゲームモードをサバイバルモードに変更します。", new ArrayList<>(), new CommandGameModeSurvival());
        utilities.addCommand("gmsall", getName(), "", "全員のゲームモードをサバイバルモードに変更します。", new ArrayList<>(), new CommandGameModeSurvivalAll());
        utilities.addCommand("tctreload", getName(), "", "コンフィグをリロードします。", new ArrayList<>(), new CommandTctReload(this));
        utilities.addCommand("barrier", getName(), "", "バリアブロックを入手します。", Arrays.asList("b", "gb"), new CommandBarrier(this));
        utilities.addCommand("start", getName(), "", "ゲームを開始します。", new ArrayList<>(), new CommandStart(this));
        utilities.addCommand("startloc", getName(), "", "ゲームを指定した場所から開始します。", new ArrayList<>(), new CommandStartLoc(this));
        utilities.addCommand("stopgame", getName(), "", "ゲームを強制終了させます。", new ArrayList<>(), new CommandStopGame(this));
        utilities.addCommand("item", getName(), "", "アイテムを入手します。", Arrays.asList("i"), new CommandItem(this));
        utilities.addCommand("shop", getName(), "", "アイテムを購入できます。", Arrays.asList("s"), new CommandShop(this));
        utilities.addCommand("stat", getName(), "", "統計を確認できます。", new ArrayList<>(), new CommandStat(this));

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
                    getGame().getReference().PLAYERDATA.put(p.getUniqueId(), data);
                    p.setFoodLevel(20);
                    p.setPlayerListName(ChatColor.GREEN + name);
                    if(MySQLStatus.isSqlEnabled() && NanamiTct.sqlConnection != null && NanamiTct.playerStats != null && NanamiTct.sqlConnection.getConnection() != null) {
                        PlayerStat stat = new TctPlayerStat(p.getUniqueId());
                        try {
                            NanamiTct.playerStats.insert(stat);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            MySQLStatus.setSqlEnabled(false);
                        }
                    }
                    bar.addPlayer(p);
                    TctUtilities utilities = NanamiTct.utilities;
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

        if(loadedPlugins != null) for(Plugin pl : loadedPlugins) {
            try {
                List<Permission> perms = plugin.getDescription().getPermissions();

                for (Permission perm : perms) {
                    try {
                        pluginManager.addPermission(perm, false);
                    } catch (IllegalArgumentException ex) {
                        getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
                    }
                }
                pluginManager.dirtyPermissibles();

                Bukkit.getPluginManager().enablePlugin(plugin);
            } catch (Throwable ex) {
                Logger.getLogger(NanamiTct.class.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
        }
    }

    public TctGame getGame() {
        return this.game;
    }

    public ITctConfiguration getTctConfig() {
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
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
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
        isLoaded = true;
    }

    public void loadPlugins() {
        Bukkit.getPluginManager().registerInterface(JavaPluginLoader.class);

        File pluginFolder = new File("plugins/" + getName() + "/plugins");

        if (pluginFolder.exists()) {
            Plugin[] plugins = Bukkit.getPluginManager().loadPlugins(pluginFolder);
            loadedPlugins = plugins;
            for (Plugin plugin : plugins) {
                try {
                    String message = String.format("Loading %s", plugin.getDescription().getFullName());
                    plugin.getLogger().info(message);
                    plugin.onLoad();
                } catch (Throwable ex) {
                    Logger.getLogger(NanamiTct.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
                }
            }
        } else {
            pluginFolder.mkdir();
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
