package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItems;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TctItemDiamond implements CustomSpecialItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private boolean attackable;
    private final boolean quickchat;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public TctItemDiamond() {
        this.index = ItemIndex.WOLVES_SHOP_ITEM_SLOT_7;
        this.isdefault = false;
        this.material = Material.DIAMOND;
        this.name = "DIAMOND";
        this.displayName = "Diamond";
        this.title = "Diamond";
        this.description = ChatColor.RED + "Wolf Item";
        this.role = GameRoles.WOLF;
        this.attackable = true;
        this.quickchat = false;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setAmount(9);
        this.item = item;
    }

    @Override
    public void onRightClick(Player player, ItemStack item) {
        if(player != null) {
            PlayerData data = NanamiTctApi.utilities.getPlayerData(player);
            if(data != null) {
                if(data.isAfterSaved()) {
                    if(data.getSavedLocation() == null) {
                        data.saveLocation(player.getLocation());
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SAVED_LOCATION);
                        return;
                    }
                    data.setTeleporting(true);
                    data.setAfterSaved(false);
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_TELEPORTING);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            data.setTeleporting(false);
                            player.teleport(data.getSavedLocation());
                        }
                    }.runTaskLater(NanamiTctApi.plugin, 40);

                    item.setAmount(item.getAmount() - 1);

                } else {
                    if(data.isTeleporting()) {
                        player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_TELEPORTING);
                        return;
                    }
                    data.saveLocation(player.getLocation());
                    player.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SAVED_LOCATION);
                    data.setAfterSaved(true);
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

    @Override
    public boolean isQuickChatItem() {
        return quickchat;
    }

}
