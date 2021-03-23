package me.clockclap.tct.game.data;

import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.boss.BossBar;

public interface PlayerData {

    public GameRole getRole();

    public GameRole getCO();

    public String getName();

    public boolean isSpectator();

    public int getQuickChatCooldown();

    public int getCoin();

    public PlayerWatcher getWatcher();

    public PlayerData getKilledBy();

    public void startQCCCountdown();

    public void setCoin(int coin);

    public void setRole(GameRole role);

    public void setCO(GameRole role);

    public void setSpectator(boolean bool);

    public void setQuickChatCooldown(int second);

    public void setWatcher(PlayerWatcher watcher);

    public void setKilledBy(PlayerData killer);

}
