package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Game {

    private NanamiTct plugin;
    private GameReference reference;
    private BossBar bar;
    private int remainingSeconds;
    private int realRemainingSeconds;
    private BukkitTask timer;
    private BukkitTask preTimer;

    public Game(NanamiTct plugin) {
        this.plugin = plugin;
        this.reference = new GameReference(plugin);
        this.remainingSeconds = 0;
        this.realRemainingSeconds = 0;
    }

    public void preStart(Location loc) {
        getReference().setGameState(GameState.STARTING);
        final int[] sec = {plugin.getTctConfig().getConfig().getInt("countdown.prestart", 10) + 1};
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(sec[0])));
        Bukkit.getServer().broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_STARTED);
        /*
        for(int i = 1; i <= sec[0]; i++) {
            int j = sec[0] - i;
            //getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(j)));
            Bukkit.getScheduler().runTaskLater (plugin, () -> getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(j))), 20 * i);
            if(j <= 0) {
                Bukkit.getScheduler().runTaskLater (plugin, () -> start(loc), 20 * i);
            }
        }
        /**/
        BukkitTask timer = new BukkitRunnable() {
            @Override
            public void run() {
                sec[0] = sec[0] - 1;
                int j = sec[0];
                getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(j)));
                if(j <= 0) {
                    start(loc);
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
        setPreTimer(timer);
        /**/

    }

    public void start(Location loc) {
        getReference().setGameState(GameState.GAMING);
        Bukkit.getServer().broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_READY_END);
        int sec = plugin.getTctConfig().getConfig().getInt("countdown.game", 240) + 1;
        setRemainingSeconds(sec);
        setRealRemainingSeconds(sec);
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_GAMING.replaceAll("%SECOND%", String.valueOf(sec)));
        BukkitTask timer = new BukkitRunnable() {
            @Override
            public void run() {
                if(getRemainingSeconds() > 0) {
                    setRealRemainingSeconds(getRealRemainingSeconds() - 1);
                } else {
                    stop(GameRoles.VILLAGER);
                    this.cancel();
                }
                if(getRemainingSeconds() > 0) {
                    setRemainingSeconds(getRemainingSeconds() - 1);
                }
                getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_GAMING.replaceAll("%SECOND%", String.valueOf(getRemainingSeconds())));
            }
        }.runTaskTimer(plugin, 0L, 20L);
        setTimer(timer);
    }

    public void stop(GameRole winners) {
        getReference().setGameState(GameState.ENDING);
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_ENDING);
        if(winners == GameRoles.NONE) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(Reference.TCT_TITLE_MAIN_NO_VICTORY, Reference.TCT_TITLE_SUB_NO_VICTORY, 5, 20, 5);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_NO_VICTORY);
            }
        }
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAMEEND_ROLE_RESULT);
        getReference().setGameState(GameState.WAITING);
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_WAITING);
    }

    public NanamiTct getPlugin() {
        return this.plugin;
    }

    public BossBar getBar() { return this.bar; }

    public int getRemainingSeconds() {
        return this.remainingSeconds;
    }

    public int getRealRemainingSeconds() {
        return this.realRemainingSeconds;
    }

    public GameReference getReference() {
        return this.reference;
    }

    public BukkitTask getTimer() {
        return this.timer;
    }

    public BukkitTask getPreTimer() {
        return this.preTimer;
    }

    public void setTimer(BukkitTask timer) {
        this.timer = timer;
    }

    public void setPreTimer(BukkitTask timer) {
        this.preTimer = timer;
    }

    public void setRemainingSeconds(int second) {
        this.remainingSeconds = second;
    }

    public void setRealRemainingSeconds(int second) {
        this.realRemainingSeconds = second;
    }

    public void setReference(GameReference reference) {
        this.reference = reference;
    }

    public void setBar(BossBar bar) { this.bar = bar; }

}
