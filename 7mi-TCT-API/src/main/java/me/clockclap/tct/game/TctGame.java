package me.clockclap.tct.game;

import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameTeam;
import me.clockclap.tct.game.role.RoleCount;
import me.clockclap.tct.item.TctLog;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public interface TctGame {

    public boolean preStart(Location location);

    public void giveItem();

    public void start(Location location);

    public void stop(GameTeam winners);

    public Plugin getPlugin();

    public BossBar getBar();

    public TctLog getLog();

    public void setLog(TctLog log);

    public Location getLocation();

    public int getElapsedTime();

    public void setLocation(Location loc);

    public int getStartingIn();

    public void setStartingIn(int value);

    public int getRemainingSeconds();

    public int getRealRemainingSeconds();

    public int getNeededPlayers();

    public List<PlayerData> getRemainingPlayers(boolean isReal);

    public void addRemainingPlayers(PlayerData data, boolean isReal);

    public void removeRemainingPlayers(PlayerData data, boolean isReal);

    public void resetRemainingPlayers(boolean isReal);

    public void resetRemainingPlayers();

    public GameReference getReference();

    public RoleCount getRoleCount();

    public BukkitTask getTimer();

    public BukkitTask getPreTimer();

    public void setRoleCount(RoleCount value);

    public void setTimer(BukkitTask timer);

    public void setPreTimer(BukkitTask timer);

    public void setRemainingSeconds(int second);

    public void setRealRemainingSeconds(int second);

    public void setReference(GameReference reference);

    public void setBar(BossBar bar);

    public List<String> getVillagersList();

    public List<String> getHealersList();

    public List<String> getDetectivesList();

    public List<String> getWolvesList();

    public List<String> getFanaticsList();

    public List<String> getFoxesList();

    public List<String> getImmoralList();

}
