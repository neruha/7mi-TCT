package me.clockclap.tct.item.blocks;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomBlock;

import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TctItemHealStation implements CustomBlock {

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

    public TctItemHealStation() {
        this.index = ItemIndex.DETECTIVES_SHOP_ITEM_SLOT_1;
        this.material = Material.REDSTONE_ORE;
        this.name = "HEAL_STATION";
        this.displayName = "Healing Station";
        this.title = "Healing Station";
        this.description = ChatColor.BLUE + "Detective Item";
        this.role = GameRoles.DETECTIVE;
        this.isdefault = false;
        this.placeable = true;
        this.breakable = false;
        this.attackable = true;
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
    public void onLeftClick(Player player) {

    }

    @Override
    public void onRightClick(Player player) {
        heal(player);
    }

    private void heal(Player p) {
        int tick = 40;
        int level;
        FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();
        if(config.getString("effect.detective-regeneration.duration").endsWith("t")) {
            String str = config.getString("effect.detective-regeneration.duration");
            str = str.substring(0, str.length() - 1);
            try {
                tick = Integer.parseInt(str);
            } catch(NumberFormatException e) {
                tick = 40;
            }
        } else if(config.getString("effect.detective-regeneration.duration").endsWith("s")) {
            String str = config.getString("effect.detective-regeneration.duration");
            str = str.substring(0, str.length() - 1);
            try {
                tick = Integer.parseInt(str) * 20;
            } catch(NumberFormatException e) {
                tick = 40;
            }
        }
        try {
            level = config.getInt("effect.detective-regeneration.level");
        } catch(Exception e) {
            level = 3;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, tick, level));
        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_HEAL_STATION_USED);
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
