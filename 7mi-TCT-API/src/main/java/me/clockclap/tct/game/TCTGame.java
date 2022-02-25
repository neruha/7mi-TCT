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

public interface TCTGame {

    boolean preStart(Location location);

    void giveItem();

    void start(Location location);

    void stop(GameTeam winners);

    Plugin getPlugin();

    BossBar getBar();

    TctLog getLog();

    void setLog(TctLog log);

    Location getLocation();

    int getElapsedTime();

    void setLocation(Location loc);

    int getStartingIn();

    void setStartingIn(int value);

    int getRemainingSeconds();

    int getRealRemainingSeconds();

    int getNeededPlayers();

    List<PlayerData> getRemainingPlayers(boolean isReal);

    void addRemainingPlayers(PlayerData data, boolean isReal);

    void removeRemainingPlayers(PlayerData data, boolean isReal);

    void resetRemainingPlayers(boolean isReal);

    void resetRemainingPlayers();

    GameReference getReference();

    RoleCount getRoleCount();

    BukkitTask getTimer();

    BukkitTask getPreTimer();

    void setRoleCount(RoleCount value);

    void setTimer(BukkitTask timer);

    void setPreTimer(BukkitTask timer);

    void setRemainingSeconds(int second);

    void setRealRemainingSeconds(int second);

    void setReference(GameReference reference);

    void setBar(BossBar bar);

    List<String> getVillagersList();

    List<String> getHealersList();

    List<String> getDetectivesList();

    List<String> getWolvesList();

    List<String> getFanaticsList();

    List<String> getFoxesList();

    List<String> getImmoralList();

}
