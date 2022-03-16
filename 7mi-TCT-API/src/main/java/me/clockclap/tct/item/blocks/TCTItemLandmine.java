package me.clockclap.tct.item.blocks;

import me.clockclap.tct.VersionUtils;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomBlock;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TCTItemLandmine implements CustomBlock {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private final GameRole role;
    private final boolean isdefault;
    private final boolean placeable;
    private final boolean breakable;
    private boolean attackable;

    private final int index;

    public TCTItemLandmine() {
        this.index = ItemIndex.WOLVES_SHOP_ITEM_SLOT_6;
        this.material = VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2) ? Material.CREEPER_HEAD : Material.getMaterial("SKULL_ITEM");
        this.name = "LANDMINE";
        this.displayName = "Landmine";
        this.title = "Landmine";
        this.description = ChatColor.RED + "Wolf Item";
        this.role = GameRoles.WOLF;
        this.isdefault = false;
        this.placeable = true;
        this.breakable = false;
        this.attackable = true;
        ItemStack item = VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2) ? new ItemStack(material, 1) : new ItemStack(material, 1, (short) SkullType.CREEPER.ordinal());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.item = item;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public ItemStack getItemStack() {
        return this.item;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isDefault() {
        return this.isdefault;
    }

    @Override
    public boolean isAttackable() {
        return this.attackable;
    }

    @Override
    public void setAttackable(boolean value) {
        this.attackable = value;
    }

    @Override
    public boolean isPlaceable() {
        return this.placeable;
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public void setItemStack(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
