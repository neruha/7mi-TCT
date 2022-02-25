package me.clockclap.tct.game.data;

import me.clockclap.tct.api.TctCooldownType;
import me.clockclap.tct.game.TCTGame;
import me.clockclap.tct.item.CustomBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public interface CustomBlockData extends CustomData {

    public TCTGame getGame();

    public Block getBlock();

    public Material getType();

    public CustomBlock getCustomBlock();

    public Location getLocation();

    public void setBlock(Block block);

    public void setLocation(Location loc);

    public void setType(Material type);

    public void runTimer(TctCooldownType type);

    public void cancelTimer();

    public BukkitRunnable getTimer();

    public boolean isEnabled();

    public int getCooldown();

    public void setTimer(BukkitRunnable timer);

    public void setCooldown(int second);

}
