package me.clockclap.tct.item.items;

import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomWeaponItem;
import me.clockclap.tct.item.ItemIndex;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TctItemStoneSword implements CustomWeaponItem {

    private float damage;
    private float speed;
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

    public TctItemStoneSword() {
        this.index = ItemIndex.ALL_SHOP_ITEM_SLOT_0;
        this.isdefault = false;
        this.material = Material.STONE_SWORD;
        this.name = "STONE_SWORD";
        this.displayName = "Stone Sword";
        this.title = "Stone Sword";
        this.description = ChatColor.AQUA + "TCT Item";
        this.role = GameRoles.VILLAGER;
        this.damage = 6.5F;
        this.speed = 1.6F;
        this.attackable = true;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 2,true);
        item.setItemMeta(meta);
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound speed = new NBTTagCompound();
        NBTTagCompound damage = new NBTTagCompound();
        speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        speed.set("Name", new NBTTagString("generic.attackSpeed"));
        speed.set("Amount", new NBTTagFloat(this.speed));
        speed.set("Operation", new NBTTagInt(0));
        speed.set("UUIDLeast", new NBTTagInt(894654));
        speed.set("UUIDMost", new NBTTagInt(2872));
        speed.set("Slot", new NBTTagString("mainhand"));
        damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage.set("Name", new NBTTagString("generic.attackDamage"));
        damage.set("Amount", new NBTTagFloat(this.damage));
        damage.set("Operation", new NBTTagInt(0));
        damage.set("UUIDLeast", new NBTTagInt(894654));
        damage.set("UUIDMost", new NBTTagInt(2872));
        damage.set("Slot", new NBTTagString("mainhand"));
        modifiers.add(speed);
        modifiers.add(damage);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        this.item = CraftItemStack.asBukkitCopy(nmsStack);
    }

    @Override
    public float getAttackDamage() {
        return this.damage;
    }

    @Override
    public float getAttackSpeed() {
        return this.speed;
    }

    @Override
    public void setAttackDamage(float value) {
        this.damage = value;
    }

    @Override
    public void setAttackSpeed(float value) {
        this.speed = value;
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
