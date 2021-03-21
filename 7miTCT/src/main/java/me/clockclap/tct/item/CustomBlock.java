package me.clockclap.tct.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public interface CustomBlock {

    public int getIndex();

    public ItemStack getItemStack();

    public Material getMaterial();

    public String getName();

    public String getDisplayName();

    public String getTitle();

    public String getDescription();

    public boolean isDefault();

    public boolean isPlaceable();

    public boolean isBreakable();

    public default void onPlace(BlockPlaceEvent e) { }

    public default void onBreak(BlockBreakEvent e) { }

    public default void onRightClick(Player player) { }

    public default void onLeftClick(Player player) { }

    public void setItemStack(ItemStack item);

    public void setMaterial(Material material);

    public void setName(String name);

    public void setDisplayName(String name);

    public void setTitle(String title);

    public void setDescription(String description);

}
