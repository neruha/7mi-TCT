package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TctItemCracker implements CustomItem {

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

    @SuppressWarnings("unchecked")
    public TctItemCracker() {
        this.index = ItemIndex.ALL_SHOP_ITEM_SLOT_7;
        this.isdefault = false;
        this.material = Material.FIREWORK;
        this.name = "CRACKER";
        this.displayName = "Cracker";
        this.title = "Cracker";
        this.description = ChatColor.AQUA + "TCT Item";
        this.role = GameRoles.VILLAGER;
        this.attackable = true;
        ItemStack item = new ItemStack(material);
        FireworkMeta meta = (FireworkMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        try {
            FireworkEffect.Type type = FireworkEffect.Type.valueOf(config.getString("fireworks.type", "BURST"));
            builder.with(type);
        } catch (Exception e) {
            builder.with(FireworkEffect.Type.BURST);
        }
        try {
            for (String color : (List<String>) config.getList("fireworks.colors", Arrays.asList("0xff0000"))) {
                int c;
                if (color.startsWith("0x")) {
                    c = (int) Long.parseLong(color, 16);
                } else {
                    c = (int) Long.parseLong(color, 10);
                }
                builder.withColor(Color.fromRGB(c));
            }
        } catch(Exception e) {
            builder.withColor(Color.RED);
        }
        try {
            for (String color : (List<String>) config.getList("fireworks.fades", Arrays.asList("0xff0000"))) {
                int c;
                if (color.startsWith("0x")) {
                    c = (int) Long.parseLong(color, 16);
                } else {
                    c = (int) Long.parseLong(color, 10);
                }
                builder.withFade(Color.fromRGB(c));
            }
        } catch(Exception e) {
            builder.withFade(Color.RED);
        }
        if(builder != null) {
            FireworkEffect effect = builder.build();
            meta.addEffect(effect);
            meta.setPower(config.getInt("fireworks.power"));
        } else {
            FireworkEffect effect = FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.RED).withFade(Color.RED).build();
            meta.addEffect(effect);
            meta.setPower(1);
        }
        item.setItemMeta(meta);
        item.setAmount(3);
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
