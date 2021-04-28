package me.clockclap.tct.game.data;

import me.clockclap.tct.api.CooldownTypes;
import me.clockclap.tct.api.TctCooldownType;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.item.CustomBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomBlockData implements CustomData {

    private BukkitRunnable timer;
    private int cooldown;
    private Location loc;
    private Block block;
    private Material type;
    private boolean enabled;

    private final Game game;
    private final CustomBlock customBlock;

    public CustomBlockData(Game game, CustomBlock customBlock, Block block) {
        this.game = game;
        this.customBlock = customBlock;
        this.block = block;
        this.type = this.block.getType();
        this.loc = this.block.getLocation();
        this.cooldown = 0;
        this.enabled = false;
    }

    public Game getGame() {
        return this.game;
    }

    public Block getBlock() {
        return this.block;
    }

    public Material getType() {
        return this.type;
    }

    public CustomBlock getCustomBlock() {
        return this.customBlock;
    }

    public Location getLocation() {
        return this.loc;
    }

    public void setBlock(Block block) {
        this.block = block;
        this.loc = this.block.getLocation();
        this.type = this.block.getType();
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public void runTimer(TctCooldownType type) {
        if(type == CooldownTypes.HEAL_STATION) {
            setCooldown(getGame().getPlugin().getTctConfig().getConfig().getInt("heal-station-cooldown", 20) + 1);
            timer = new BukkitRunnable() {
                @Override
                public void run() {
                    if (getCooldown() > 0) {
                        setCooldown(getCooldown() - 1);
                    } else {
                        this.cancel();
                    }
                }
            };
            timer.runTaskTimer(this.game.getPlugin(), 0L, 20L);
        } else if(type == CooldownTypes.LANDMINE) {
            timer = new BukkitRunnable() {
                @Override
                public void run() {
                    enabled = true;
                }
            };
            timer.runTaskLater(this.game.getPlugin(), this.game.getPlugin().getTctConfig().getConfig().getInt("landmine-cooldown", 5) * 20L);
        }
    }

    public void cancelTimer() {
        this.timer.cancel();
    }

    public BukkitRunnable getTimer() {
        return this.timer;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setTimer(BukkitRunnable timer) {
        this.timer = timer;
    }

    public void setCooldown(int second) {
        this.cooldown = second;
    }

}
