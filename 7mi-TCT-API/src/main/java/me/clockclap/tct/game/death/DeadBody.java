package me.clockclap.tct.game.death;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.VersionUtils;
import me.clockclap.tct.game.TCTGame;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DeadBody {

    private final TCTGame game;
    private final PlayerData data;
    private final String name;
    private final TctDeathCause cause;
    private final GameRole role;
    private boolean isFound;
    private Location loc;
    private final Material beforeBlockType0;
    private final Material beforeBlockType1;
    private final byte beforeBlockByteData0;
    private BlockData beforeBlockData0;
    private final byte beforeBlockByteData1;
    private BlockData beforeBlockData1;
    private int time;
    private BukkitRunnable runnable;
    private List<String> killedPlayers;
    private boolean damaged;
    private boolean fake;

    public DeadBody(TCTGame game, PlayerData data, TctDeathCause cause, Location loc) {
        this.game = game;
        this.data = data;
        this.name = data.getPlayer().getDisplayName();
        this.cause = cause;
        this.role = data.getRole();
        this.isFound = false;
        this.loc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        this.time = 0;
        this.loc.subtract(0, 1, 0);

        Block block0 = data.getPlayer().getWorld().getBlockAt(this.loc);

        this.beforeBlockType0 = block0.getType();
        this.beforeBlockByteData0 = block0.getData();
        if (VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2)) this.beforeBlockData0 = block0.getBlockData();

        this.loc.add(0, 1, 0);
        Block block1 = data.getPlayer().getWorld().getBlockAt(this.loc);

        this.beforeBlockType1 = block1.getType();
        this.beforeBlockByteData1 = block1.getData();
        if (VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2)) this.beforeBlockData1 = block1.getBlockData();

        this.killedPlayers = new ArrayList<>();
        this.damaged = false;
        this.fake = false;
    }

    public TCTGame getGame() {
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

    public DeadBody setDamaged(boolean value) {
        this.damaged = value;

        return this;
    }

    public DeadBody setFake(boolean value) {
        this.fake = value;

        return this;
    }

    public List<String> getKilledPlayers() {
        return this.killedPlayers;
    }

    public DeadBody setKilledPlayers(List<String> list) {
        this.killedPlayers = list;

        return this;
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
        if (value) {
            Sign sign = (Sign) loc.getWorld().getBlockAt(loc).getState();
            sign.setLine(0, "");
            sign.setLine(1, "[FOUND]");
            sign.setLine(2, "死体");
            sign.setLine(3, "");
            sign.update();
            if (getCause() == TctDeathCause.KILL) {
                getGame().setRemainingSeconds(getGame().getRemainingSeconds() + NanamiTctApi.config.getConfig().getInt("countdown.addcount.kill", 20));
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
            } catch (Exception ignored) {
            }
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
        loc.getWorld().getBlockAt(loc).setType(VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2) ? Material.OAK_SIGN : Material.getMaterial("SIGN_POST"));
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

        Block block0 = loc.getWorld().getBlockAt(loc);

        block0.setType(getBeforeBlockTypeBottom());

        if (VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2)) {
            block0.setBlockData(getBeforeBlockDataBottom());
        } else {
            try {
                Method method = Block.class.getMethod("setData", byte.class);
                method.invoke(block0, getBeforeBlockByteDataBottom());

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        loc.clone().add(0, 1, 0);

        Block block1 = loc.getWorld().getBlockAt(loc);
        block1.setType(getBeforeBlockTypeTop());

        if (VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2)) {
            block1.setBlockData(getBeforeBlockDataTop());
        } else {
            try {
                Method method = Block.class.getMethod("setData", byte.class);
                method.invoke(block1, getBeforeBlockByteDataTop());

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

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
    public static void removeAll(TCTGame game) {
        for (DeadBody deadBody : game.getReference().DEADBODIES) {
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

    public byte getBeforeBlockByteDataBottom() {
        return beforeBlockByteData0;
    }

    public byte getBeforeBlockByteDataTop() {
        return beforeBlockByteData1;
    }

    public BlockData getBeforeBlockDataBottom() {
        return beforeBlockData0;
    }

    public BlockData getBeforeBlockDataTop() {
        return beforeBlockData1;
    }
}