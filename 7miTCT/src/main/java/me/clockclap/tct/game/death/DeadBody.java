package me.clockclap.tct.game.death;

import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DeadBody {

    private Game game;
    private PlayerData data;
    private String name;
    private TctDeathCause cause;
    private GameRole role;
    private boolean isFound;
    private Location loc;
    private Material beforeBlockType0;
    private Material beforeBlockType1;
    private byte beforeBlockData0;
    private byte beforeBlockData1;
    private int time;
    private BukkitRunnable runnable;
    private List<String> killedPlayers;
    private boolean damaged;
    private boolean fake;

    public DeadBody(Game game, PlayerData data, TctDeathCause cause, Location loc) {
        this.game = game;
        this.data = data;
        this.name = data.getPlayer().getDisplayName();
        this.cause = cause;
        this.role = data.getRole();
        this.isFound = false;
        this.loc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        this.time = 0;
        this.loc.subtract(0, 1, 0);
        this.beforeBlockType0 = data.getPlayer().getWorld().getBlockAt(this.loc).getType();
        this.beforeBlockData0 = data.getPlayer().getWorld().getBlockAt(this.loc).getData();
        this.loc.add(0, 1, 0);
        this.beforeBlockType1 = data.getPlayer().getWorld().getBlockAt(this.loc).getType();
        this.beforeBlockData1 = data.getPlayer().getWorld().getBlockAt(this.loc).getData();
        this.killedPlayers = new ArrayList<>();
        this.damaged = false;
        this.fake = false;
    }

    public Game getGame() {
        return this.game;
    }

    public TctDeathCause getCause() {
        return this.cause;
    }

    public GameRole getRole() {
        return this.role;
    }

    public Player getPlayer() {
        return this.data.getPlayer();
    }

    public boolean isDamaged() {
        return this.damaged;
    }

    public boolean isFake() {
        return this.fake;
    }

    public void setDamaged(boolean value) {
        this.damaged = value;
    }

    public void setFake(boolean value) {
        this.fake = value;
    }

    public List<String> getKilledPlayers() {
        return this.killedPlayers;
    }

    public void setKilledPlayers(List<String> list) {
        this.killedPlayers = list;
    }

    public void addKilledPlayer(String name) {
        this.killedPlayers.add(name);
    }

    public void removeKilledPlayer(String name) {
        this.killedPlayers.remove(name);
    }

    public void removeKilledPlayer(int index) {
        this.killedPlayers.remove(index);
    }

    public void resetKilledPlayers() {
        this.killedPlayers = new ArrayList<>();
    }

    public boolean isFound() {
        return this.isFound;
    }

    public void setFound(boolean value) {
        this.isFound = value;
        if(value) {
            Sign sign = (Sign) loc.getWorld().getBlockAt(loc).getState();
            sign.setLine(0, "");
            sign.setLine(1, "[FOUND]");
            sign.setLine(2, "死体");
            sign.setLine(3, "");
            sign.update();
            if(getCause() == TctDeathCause.KILL) {
                getGame().setRemainingSeconds(getGame().getRemainingSeconds() + getGame().getPlugin().getTctConfig().getConfig().getInt("countdown.addcount.kill", 20));
            }
            try {
                if (getGame().getRemainingPlayers(false) != null) {
                    for (PlayerData data : getGame().getRemainingPlayers(false)) {
                        if (data != null) {
                            if (data.getName().equalsIgnoreCase(this.getName())) {
                                getGame().removeRemainingPlayers(data, false);
                            }
                        }
                    }
                }
            } catch(Exception ignored) { }
        }
    }

    public Location getLocation() {
        return this.loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public void process() {
        loc.subtract(0, 1, 0);
        loc.getWorld().getBlockAt(loc).setType(Material.BEDROCK);
        loc.add(0, 1, 0);
        loc.getWorld().getBlockAt(loc).setType(Material.SIGN_POST);
        Sign sign = (Sign) data.getPlayer().getWorld().getBlockAt(loc).getState();
        sign.setLine(0, "");
        sign.setLine(1, "[UNFOUND]");
        sign.setLine(2, "死体");
        sign.setLine(3, "");
        sign.update();
        resetTimeAfterDeath();
        startCount();
    }

    public void remove() {
        loc.subtract(0, 1, 0);
        loc.getWorld().getBlockAt(loc).setType(getBeforeBlockTypeBottom());
        loc.getWorld().getBlockAt(loc).setData(getBeforeBlockDataBottom());
        loc.add(0, 1, 0);
        loc.getWorld().getBlockAt(loc).setType(getBeforeBlockTypeTop());
        loc.getWorld().getBlockAt(loc).setData(getBeforeBlockDataTop());
        cancelCount();
        resetTimeAfterDeath();
    }

    public int getTimeAfterDeath() {
        return this.time;
    }

    public void addTimeAfterDeath(int time) {
        this.time = this.time + time;
    }

    public void resetTimeAfterDeath() {
        this.time = 0;
    }

    public void startCount() {
        this.time = getTimeAfterDeath() - 1;
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                addTimeAfterDeath(1);
            }
        };
        runnable.runTaskTimer(this.game.getPlugin(), 0, 20);
    }

    public void cancelCount() {
        runnable.cancel();
    }

    public String getName() {
        return this.name;
    }

    @Deprecated
    public static void removeAll(Game game) {
        for(DeadBody deadBody : game.getReference().DEADBODIES) {
            deadBody.remove();
        }
        game.getReference().DEADBODIES = new ArrayList<>();
    }

    public Material getBeforeBlockTypeBottom() {
        return beforeBlockType0;
    }

    public Material getBeforeBlockTypeTop() {
        return beforeBlockType1;
    }

    public byte getBeforeBlockDataBottom() {
        return beforeBlockData0;
    }

    public byte getBeforeBlockDataTop() {
        return beforeBlockData1;
    }

}
