package me.clockclap.tct.item;

import me.clockclap.tct.game.role.GameRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public interface CustomBlock extends CustomItem {

    public boolean isPlaceable();

    public boolean isBreakable();

    public default void onPlace(BlockPlaceEvent e) { }

    public default void onBreak(BlockBreakEvent e) { }

    public default void onRightClick(Player player) { }

    public default void onLeftClick(Player player) { }

}
