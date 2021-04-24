package me.clockclap.tct.item.items;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class TctItemDiamondHelmet implements CustomItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private boolean attackable;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public TctItemDiamondHelmet() {
        this.index = ItemIndex.DETECTIVES_SHOP_ITEM_SLOT_4;
        this.isdefault = true;
        this.material = Material.DIAMOND_HELMET;
        this.name = "DIAMOND_HELMET";
        this.displayName = Reference.TCT_CONFIRM_DETECTIVE;
        this.title = Reference.TCT_CONFIRM_DETECTIVE;
        this.description = ChatColor.BLUE + "Detective Item";
        this.role = GameRoles.DETECTIVE;
        this.attackable = true;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
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
    public GameRole getRole() {
        return this.role;
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
