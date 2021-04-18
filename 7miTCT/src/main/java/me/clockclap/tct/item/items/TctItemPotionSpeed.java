package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TctItemPotionSpeed implements CustomItem {

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

    public TctItemPotionSpeed() {
        this.index = ItemIndex.ALL_SHOP_ITEM_SLOT_2;
        this.isdefault = false;
        this.material = Material.POTION;
        this.name = "SPEED_POTION";
        this.displayName = "Speed Potion";
        this.title = "Speed Potion";
        this.description = ChatColor.AQUA + "TCT Item";
        this.role = GameRoles.VILLAGER;
        this.attackable = true;
        ItemStack item = new ItemStack(material);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        int tick = 3600;
        int level;
        FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();
        try {
            if (config.getString("potion-effect.speed.duration").endsWith("t")) {
                String str = config.getString("potion-effect.speed.duration");
                str = str.substring(0, str.length() - 1);
                try {
                    tick = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    tick = 3600;
                }
            } else if (config.getString("potion-effect.speed.duration").endsWith("s")) {
                String str = config.getString("potion-effect.speed.duration");
                str = str.substring(0, str.length() - 1);
                try {
                    tick = Integer.parseInt(str) * 20;
                } catch (NumberFormatException e) {
                    tick = 3600;
                }
            }
        } catch(NullPointerException e) {
            tick = 3600;
        }
        try {
            level = config.getInt("potion-effect.speed.level");
        } catch(Exception e) {
            level = 1;
        }
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, tick, level), true);
        meta.setColor(Color.fromRGB(150, 150, 255));
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
        this.attackable = true;
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
