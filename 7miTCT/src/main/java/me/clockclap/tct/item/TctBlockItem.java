package me.clockclap.tct.item;

import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TctBlockItem implements CustomBlock {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private GameRole role;
    private boolean isdefault;
    private boolean placeable;
    private boolean breakable;
    private boolean attackable;

    private final int index;

    public TctBlockItem(int index) {
        this(index, false, Material.APPLE, "", "", "", "", GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault) {
        this(index, isdefault, Material.APPLE, "", "", "", "", GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material) {
        this(index, isdefault, material, "", "", "", "", GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name) {
        this(index, isdefault, material, name, "", "", "", GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName) {
        this(index, isdefault, material, name, displayName, "", "", GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName, String title) {
        this(index, isdefault, material, name, displayName, title, "", GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName, String title, String description) {
        this(index, isdefault, material, name, displayName, title, description, GameRoles.NONE, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName, String title, String description, GameRole role) {
        this(index, isdefault, material, name, displayName, title, description, role, false, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName, String title, String description, GameRole role, boolean placeable) {
        this(index, isdefault, material, name, displayName, title, description, role, placeable, false, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName, String title, String description, GameRole role, boolean placeable, boolean breakable) {
        this(index, isdefault, material, name, displayName, title, description, role, placeable, breakable, false);
    }

    public TctBlockItem(int index, boolean isdefault, Material material, String name, String displayName, String title, String description, GameRole role, boolean placeable, boolean breakable, boolean attackable) {
        this.index = index;
        this.material = material;
        this.name = name;
        this.displayName = displayName;
        this.title = title;
        this.description = description;
        this.role = role;
        this.isdefault = isdefault;
        this.placeable = placeable;
        this.breakable = breakable;
        this.attackable = attackable;
        ItemStack item = new ItemStack(material);
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
