package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TctItemSeed implements CustomSpecialItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private boolean attackable;
    private boolean quickchat;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public TctItemSeed() {
        this.index = ItemIndex.DETECTIVES_SHOP_ITEM_SLOT_5;
        this.isdefault = false;
        this.material = Material.SEEDS;
        this.name = "SEED";
        this.displayName = "Seed";
        this.title = "Seed";
        this.description = ChatColor.BLUE + "Detective Item";
        this.role = GameRoles.DETECTIVE;
        this.attackable = false;
        this.quickchat = false;
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
    public void onRightClick(Player player) {
        if(player != null) {
            Game game = NanamiTct.plugin.getGame();
            PlayerData data = game.getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(player.getName()));
            boolean foundWolf = false;
            for(Player pl : Bukkit.getOnlinePlayers()) {
                if(pl != null) {
                    PlayerData dat = game.getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(pl.getName()));
                    if(dat != null) {
                        if(!dat.isSpectator() && !dat.isInvisible()) {
                            double maxFar = NanamiTct.plugin.getTctConfig().getConfig().getInt("seed-range", 5);
                            double far = player.getLocation().distance(pl.getLocation());
                            if(far <= maxFar) {
                                if(dat.getRole() == GameRoles.WOLF) {
                                    foundWolf = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if(foundWolf) {
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_IS_WOLF.replaceAll("%DISTANCE%", String.valueOf(NanamiTct.plugin.getTctConfig().getConfig().getInt("seed-range", 5))));
            } else {
                player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ISNT_WOLF.replaceAll("%DISTANCE%", String.valueOf(NanamiTct.plugin.getTctConfig().getConfig().getInt("seed-range", 5))));
            }
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                    int amt = item.getAmount() - 1;
                    item.setAmount(amt);
                    player.getInventory().setItem(i, amt > 0 ? item : null);
                    player.updateInventory();
                    break;
                }
            }
        }
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
    public boolean isQuickChatItem() {
        return this.quickchat;
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
