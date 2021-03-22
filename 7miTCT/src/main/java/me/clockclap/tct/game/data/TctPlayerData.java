package me.clockclap.tct.game.data;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

public class TctPlayerData implements PlayerData {

    private NanamiTct plugin;

    private GameRole role;
    private GameRole co;
    private String name;
    private boolean spec;
    private int quickchatcooldown;
    private int coin;
    private PlayerWatcher watcher;

    public TctPlayerData(NanamiTct plugin, GameRole role, String name) {
        this.plugin = plugin;
        this.role = role;
        this.name = name;
        this.co = GameRoles.NONE;
        this.spec = true;
        this.quickchatcooldown = 0;
        this.coin = 0;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public GameRole getCO() {
        return this.co;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isSpectator() {
        return this.spec;
    }

    @Override
    public int getQuickChatCooldown() {
        return this.quickchatcooldown;
    }

    @Override
    public int getCoin() {
        return this.coin;
    }

    @Override
    public PlayerWatcher getWatcher() {
        return this.watcher;
    }

    @Override
    public void startQCCCountdown() {
        int defaultSec = plugin.getTctConfig().getConfig().getInt("quickchat-cooldown", 20) + 1;
        setQuickChatCooldown(defaultSec);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(getQuickChatCooldown() > 0) {
                    setQuickChatCooldown(getQuickChatCooldown() - 1);
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void setCoin(int coin) {
        this.coin = coin;
    }

    @Override
    public void setRole(GameRole role) {
        this.role = role;
    }

    @Override
    public void setCO(GameRole role) {
        this.co = role;
    }

    @Override
    public void setSpectator(boolean bool) {
        this.spec = bool;
    }

    @Override
    public void setQuickChatCooldown(int second) {
        this.quickchatcooldown = second;
    }

    @Override
    public void setWatcher(PlayerWatcher watcher) {
        this.watcher = watcher;
    }

}
