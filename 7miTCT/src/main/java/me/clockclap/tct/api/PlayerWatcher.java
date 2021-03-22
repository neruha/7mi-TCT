package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerWatcher {

    private Player player;
    private BukkitRunnable runnable;
    private Location lastBlock;

    private final Game game;
    private final PlayerData data;

    public PlayerWatcher(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.data = this.game.getReference().PLAYERDATA.get(this.player.getName());
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerData getPlayerData() {
        return this.data;
    }

    public Game getGame() {
        return this.game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Deprecated
    public void startWatch() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = player.getPlayer().getLocation();
                loc.setY((int) loc.getY());
                if (loc.subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
                    return;
                }
                setLastStoodBlock(loc);
            }
        };
        runnable.runTaskTimer(this.game.getPlugin(), 0, 1);
    }

    public void cancelPlayerWatcher() {
        runnable.cancel();
    }

    @Deprecated
    public Location getLastStoodBlock() {
        return this.lastBlock;
    }

    public void setLastStoodBlock(Location loc) {
        this.lastBlock = loc;
    }

}
