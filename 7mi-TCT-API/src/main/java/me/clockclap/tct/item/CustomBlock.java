package me.clockclap.tct.item;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public interface CustomBlock extends CustomItem {

    public boolean isPlaceable();

    public boolean isBreakable();

    public default void onPlace(BlockPlaceEvent e) { }

    public default void onBreak(BlockBreakEvent e) { }

    public default void onRightClick(Player player) { }

    public default void onLeftClick(Player player) { }

}
